package com.jobnav.api.feature.user.service;

import com.jobnav.api.exception.BusinessServiceException;
import com.jobnav.api.feature.user.repository.entity.User;
import com.querydsl.core.types.Predicate;
import io.vavr.control.Either;

public interface UserService {

    User save(final User user);

    Either<BusinessServiceException, User> findOne(final Predicate predicate);

}
