package com.jobnav.api.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jobnav.api.constant.SwaggerConstant;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import org.springframework.util.concurrent.ListenableFuture;
import springfox.documentation.builders.AlternateTypeBuilder;
import springfox.documentation.builders.AlternateTypePropertyBuilder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jobnav.api.constant.SwaggerConstant.DEFAULT_VALUE_FIELD;
import static com.jobnav.api.constant.SwaggerConstant.EXAMPLE_FIELD;
import static com.jobnav.api.constant.SwaggerConstant.VALUE_FIELD;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jobnav.api"))
                .paths(PathSelectors.any()).build()
                .securitySchemes(Lists.newArrayList(new ApiKey("token", "Authorization", "header")))
                .securityContexts(Lists.newArrayList(SecurityContext.builder().securityReferences(defaultAuth()).build()))
                .forCodeGeneration(true)
                .apiInfo(apiInfo())
                .directModelSubstitute(LocalTime.class, String.class)
                .genericModelSubstitutes(ListenableFuture.class)
                .useDefaultResponseMessages(false);
    }

    @Bean
    public UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(true)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

    @Bean
    public AlternateTypeRuleConvention pageableConvention(final TypeResolver resolver) {
        return new AlternateTypeRuleConvention() {
            @Override
            public int getOrder() {
                return Ordered.HIGHEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules() {
                return Collections.singletonList(AlternateTypeRules.newRule(resolver.resolve(Pageable.class), resolver.resolve(pageableMixin()))
                );
            }
        };
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("JOBNAV BACKEND")
                .contact(new Contact("jobnav", "", "")).version("1.0").build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        return Lists.newArrayList(new SecurityReference("token", new AuthorizationScope[]{authorizationScope}));
    }

    private Type pageableMixin() {
        return new AlternateTypeBuilder()
                .fullyQualifiedClassName(String.format("%s.generated.%s", Pageable.class.getPackage().getName(), Pageable.class.getSimpleName()))
                .withProperties(Stream.of(property(Integer.class, "page", ImmutableMap.of(VALUE_FIELD, "Page Number", EXAMPLE_FIELD, "1", DEFAULT_VALUE_FIELD, "1")),
                                property(Integer.class, "size", ImmutableMap.of(VALUE_FIELD, "Page Size", SwaggerConstant.EXAMPLE_FIELD, "1", DEFAULT_VALUE_FIELD, "1")),
                                property(String[].class, "sort", ImmutableMap.of(VALUE_FIELD, "Sort Properties Name", EXAMPLE_FIELD, "eventName")))
                        .collect(Collectors.toList())).build();
    }

    private AlternateTypePropertyBuilder property(final Class<?> type, final String name, final Map<String, Object> parameters) {
        return new AlternateTypePropertyBuilder().withName(name).withType(type).withCanRead(true).withCanWrite(true)
                .withAnnotations(Collections.singletonList(AnnotationProxy.of(ApiParam.class, parameters)));
    }

    @Accessors(fluent = true)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AnnotationProxy implements Annotation, InvocationHandler {
        @Getter
        private final Class<? extends Annotation> annotationType;
        private final Map<String, Object> values;

        public static Annotation of(final Class<ApiParam> annotation, Map<String, Object> values) {
            return (ApiParam) Proxy.newProxyInstance(annotation.getClassLoader(),
                    new Class[]{annotation}, new AnnotationProxy(annotation, ImmutableMap.<String, Object>builder().putAll(values).put("annotationType", annotation).build()));
        }

        public Object invoke(final Object proxy, final Method method, final Object[] args) {
            return values.getOrDefault(method.getName(), method.getDefaultValue());
        }
    }
}
