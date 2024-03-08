package com.jobnav.api.feature.user.service;

import com.jobnav.api.exception.BusinessServiceException;
import com.jobnav.api.feature.user.repository.entity.UserProfile;
import com.querydsl.core.types.Predicate;
import io.vavr.control.Either;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserProfileService {

    UserProfile save(final UserProfile userProfile);

    Page<UserProfile> findAll(final Predicate predicate, final Pageable pageable);

    Either<BusinessServiceException, UserProfile> findOne(final Predicate predicate);

}
