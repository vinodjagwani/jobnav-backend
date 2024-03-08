package com.jobnav.api.feature.user.web.facade;

import com.google.common.collect.Lists;
import com.jobnav.api.config.EmailPropConfig;
import com.jobnav.api.feature.notification.dto.EmailDataRequest;
import com.jobnav.api.feature.notification.service.NotificationService;
import com.jobnav.api.feature.user.repository.entity.QUser;
import com.jobnav.api.feature.user.repository.entity.User;
import com.jobnav.api.feature.user.repository.entity.UserTypeEnum;
import com.jobnav.api.feature.user.service.UserService;
import com.jobnav.api.feature.user.web.dto.UserSignUpRequest;
import com.jobnav.api.feature.user.web.dto.UserSignUpResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSignUpFacade {

    EmailPropConfig emailPropConfig;

    UserService userService;

    NotificationService<EmailDataRequest> notificationService;

    @Transactional(rollbackFor = Exception.class)
    public UserSignUpResponse signUpUser(final UserSignUpRequest request) {
        log.trace("Start sign up user with request [{}]", request);
        final User savedUser = userService.save(buildUser(request));
        final UserSignUpResponse response = buildUserSignUpResponse(savedUser);
        // notificationService.prepareAndSendMessage(buildEmailDataRequest(response), NotificationType.EMAIL, Locale.forLanguageTag("en"), "/job-applicant-register-email.ftl");
        log.trace("End sign up user with response [{}]", response);
        return response;
    }

    private User buildUser(final UserSignUpRequest request) {
        final User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUserType(request.getUserType());
        Optional.of(request).filter(fr -> UserTypeEnum.HIRING_MANAGER.name().equals(fr.getUserType()))
                .ifPresent(r -> {
                    final User company = userService.findOne(QUser.user.userId.eq(r.getCompanyId())).getOrElseThrow(ex -> ex);
                    user.setCompanyId(company.getCompanyId());
                });
        return user;
    }

    private UserSignUpResponse buildUserSignUpResponse(final User user) {
        final UserSignUpResponse response = new UserSignUpResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setUserType(user.getUserType());
        return response;
    }

    private EmailDataRequest buildEmailDataRequest(final UserSignUpResponse response) {
        return EmailDataRequest.builder()
                .content(Map.of("user", response))
                .to(Lists.newArrayList(response.getEmail()))
                .from(emailPropConfig.getMailFrom())
                .subject(emailPropConfig.getJobApplicantRegisterEmailSubject())
                .build();
    }
}
