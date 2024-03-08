package com.jobnav.api.validator;

import com.jobnav.api.feature.user.repository.entity.UserTypeEnum;
import com.jobnav.api.feature.user.web.dto.UserSignUpRequest;
import com.jobnav.api.validator.annotation.ValidHiringManager;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HiringManagerValidator implements ConstraintValidator<ValidHiringManager, UserSignUpRequest> {

    private ValidHiringManager annotation;

    @Override
    public void initialize(final ValidHiringManager annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(final UserSignUpRequest request, final ConstraintValidatorContext context) {
        return !UserTypeEnum.HIRING_MANAGER.name().equals(request.getUserType())
                || !StringUtils.isBlank(request.getCompanyId());
    }
}
