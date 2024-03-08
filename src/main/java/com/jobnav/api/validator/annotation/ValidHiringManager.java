package com.jobnav.api.validator.annotation;

import com.jobnav.api.validator.HiringManagerValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE, ElementType.TYPE_PARAMETER})
@Constraint(validatedBy = {HiringManagerValidator.class})
public @interface ValidHiringManager {

    String message() default "{com.jobnav.api.validator.annotation.ValidHiringManager.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
