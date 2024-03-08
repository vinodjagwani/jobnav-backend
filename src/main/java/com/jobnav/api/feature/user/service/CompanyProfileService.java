package com.jobnav.api.feature.user.service;

import com.jobnav.api.exception.BusinessServiceException;
import com.jobnav.api.feature.user.repository.entity.CompanyProfile;
import com.querydsl.core.types.Predicate;
import io.vavr.control.Either;

public interface CompanyProfileService {

    CompanyProfile save(final CompanyProfile companyProfile);

    Either<BusinessServiceException, CompanyProfile> findOne(final Predicate predicate);
}
