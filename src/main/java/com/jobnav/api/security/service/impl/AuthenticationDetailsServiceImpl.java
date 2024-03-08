package com.jobnav.api.security.service.impl;

import com.jobnav.api.exception.BusinessServiceException;
import com.jobnav.api.exception.dto.ErrorCodeEnum;
import com.jobnav.api.feature.user.repository.UserRepository;
import com.jobnav.api.feature.user.repository.entity.QUser;
import com.jobnav.api.feature.user.repository.entity.User;
import com.jobnav.api.security.service.AuthenticationDetailsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationDetailsServiceImpl implements AuthenticationDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        log.trace("Start loading user by username [{}]", username);
        final User user = userRepository.findOne(QUser.user.username.eq(username))
                .orElseThrow(() -> new BusinessServiceException(ErrorCodeEnum.ENTITY_NOT_FOUND, "user not found"));
        log.trace("Finish loading user by jobApplicantId [{}]", user.getUserId());
        return user;
    }
}
