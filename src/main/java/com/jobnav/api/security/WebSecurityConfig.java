package com.jobnav.api.security;


import com.jobnav.api.feature.user.repository.UserRepository;
import com.jobnav.api.security.service.AuthenticationDetailsService;
import com.jobnav.api.security.service.impl.SocialOAuth2UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    TokenIntrospect tokenIntrospect;

    UserRepository userRepository;

    AuthenticationDetailsService authenticationDetailsService;

    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(authenticationDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().accessDeniedHandler((request, response, e) -> response.sendError(SC_UNAUTHORIZED))
                .and().formLogin()
                .disable()
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .cors()
                .disable()
                .addFilterBefore(new TokenExceptionFilter(), BearerTokenAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/api/v1/auth/**",
                        "/oauth/**", "/oauth2/**", "/social/**",
                        "/api/v1/job-mailing/**",
                        "/api/v1/contact-form/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/jobs")
                .permitAll()
                .antMatchers("/api/**")
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .opaqueToken(tokenSpec -> tokenSpec.introspector(tokenIntrospect))
                .and()
                .oauth2Login()
                .tokenEndpoint()
                .accessTokenResponseClient(authorizationCodeTokenResponseClient())
                .and()
                .successHandler(customAuthenticationSuccessHandler)
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .and()
                .userInfoEndpoint()
                .userService(new SocialOAuth2UserService(userRepository, passwordEncoder()))
                .and()
                .redirectionEndpoint()
                .baseUri("/social/**");
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs/**", "/configuration/ui",
                "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/swagger-ui/", "/swagger-ui/**",
                "/webjars/**", "/actuator/**", "/health/**", "/info/**");
    }

    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> authorizationCodeTokenResponseClient() {
        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter =
                new OAuth2AccessTokenResponseHttpMessageConverter();
        tokenResponseHttpMessageConverter.setTokenResponseConverter(new OAuth2AccessTokenResponseConverter());

        RestTemplate restTemplate = new RestTemplate(Arrays.asList(
                new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

        DefaultAuthorizationCodeTokenResponseClient tokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        tokenResponseClient.setRestOperations(restTemplate);

        return tokenResponseClient;
    }
}
