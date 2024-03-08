package com.jobnav.api.feature.user.service.impl;

import com.jobnav.api.exception.BusinessServiceException;
import com.jobnav.api.exception.dto.ErrorCodeEnum;
import com.jobnav.api.feature.user.repository.UserRepository;
import com.jobnav.api.feature.user.repository.entity.User;
import com.jobnav.api.feature.user.service.UserService;
import com.querydsl.core.types.Predicate;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    PasswordEncoder passwordEncoder;

    UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User save(final User user) {
        log.debug("Start saving user with userId [{}]", user.getUserId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        final User savedUser = userRepository.save(user);
        log.debug("Finish saving user with userId [{}]", user.getUserId());
        return savedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public Either<BusinessServiceException, User> findOne(final Predicate predicate) {
        log.debug("Start querying user with predicate [{}]", predicate);
        final Optional<User> optionalUser = userRepository.findOne(predicate);
        if (optionalUser.isPresent()) {
            return Either.right(optionalUser.orElse(null));
        }
        log.debug("Finish querying user with predicate [{}]", predicate);
        return Either.left(new BusinessServiceException(ErrorCodeEnum.ENTITY_NOT_FOUND, "user not found in the system"));
    }
}
