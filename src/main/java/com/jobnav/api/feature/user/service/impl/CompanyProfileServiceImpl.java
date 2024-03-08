package com.jobnav.api.feature.user.service.impl;

import com.jobnav.api.exception.BusinessServiceException;
import com.jobnav.api.exception.dto.ErrorCodeEnum;
import com.jobnav.api.feature.user.repository.CompanyProfileRepository;
import com.jobnav.api.feature.user.repository.entity.CompanyProfile;
import com.jobnav.api.feature.user.service.CompanyProfileService;
import com.querydsl.core.types.Predicate;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyProfileServiceImpl implements CompanyProfileService {

    CompanyProfileRepository companyProfileRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyProfile save(final CompanyProfile companyProfile) {
        log.debug("Start saving company profile data with companyProfileId [{}]", companyProfile.getCompanyProfileId());
        final CompanyProfile saveCompanyProfile = companyProfileRepository.save(companyProfile);
        log.debug("Finish saving company profile data with companyProfileId [{}]", saveCompanyProfile.getCompanyProfileId());
        return saveCompanyProfile;
    }

    @Override
    public Either<BusinessServiceException, CompanyProfile> findOne(final Predicate predicate) {
        log.debug("Start querying company profile with predicate [{}]", predicate);
        final Optional<CompanyProfile> optionalCompanyProfile = companyProfileRepository.findOne(predicate);
        if (optionalCompanyProfile.isPresent()) {
            return Either.right(optionalCompanyProfile.orElse(null));
        }
        log.debug("Finish querying company profile with predicate [{}]", predicate);
        return Either.left(new BusinessServiceException(ErrorCodeEnum.ENTITY_NOT_FOUND, "companyProfile not found in the system"));
    }
}
