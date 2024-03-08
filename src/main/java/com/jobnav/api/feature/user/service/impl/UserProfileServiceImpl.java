package com.jobnav.api.feature.user.service.impl;

import com.jobnav.api.exception.BusinessServiceException;
import com.jobnav.api.exception.dto.ErrorCodeEnum;
import com.jobnav.api.feature.user.repository.UserProfileRepository;
import com.jobnav.api.feature.user.repository.entity.UserProfile;
import com.jobnav.api.feature.user.service.UserProfileService;
import com.querydsl.core.types.Predicate;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileServiceImpl implements UserProfileService {

    UserProfileRepository userProfileRepository;

    @Override
    @Transactional(readOnly = true)
    public Either<BusinessServiceException, UserProfile> findOne(final Predicate predicate) {
        log.debug("Start querying user profile with predicate [{}]", predicate);
        final Optional<UserProfile> optionalJobApplicantProfile = userProfileRepository.findOne(predicate);
        if (optionalJobApplicantProfile.isPresent()) {
            return Either.right(optionalJobApplicantProfile.orElse(null));
        }
        log.debug("Finish querying user profile with predicate [{}]", predicate);
        return Either.left(new BusinessServiceException(ErrorCodeEnum.ENTITY_NOT_FOUND, "user not found in the system"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserProfile save(final UserProfile userProfile) {
        log.debug("Start saving user profile detail with userProfileId [{}]", userProfile.getUserProfileId());
        final UserProfile savedUserProfile = userProfileRepository.save(userProfile);
        log.debug("Finish saving user profile detail with userProfileId [{}]", userProfile.getUserProfileId());
        return savedUserProfile;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProfile> findAll(final Predicate predicate, final Pageable pageable) {
        log.debug("Start querying user profiles with predicate [{}] and pageable [{}]", predicate, pageable);
        final Page<UserProfile> userProfiles = userProfileRepository.findAll(predicate, pageable);
        log.debug("Finish querying user profiles with no of records [{}]", userProfiles.getTotalElements());
        return userProfiles;
    }
}
