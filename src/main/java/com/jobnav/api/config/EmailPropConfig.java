package com.jobnav.api.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "spring.mail.custom")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmailPropConfig {

    String mailFrom;

    String jobApplicantRegisterEmailSubject;

}
