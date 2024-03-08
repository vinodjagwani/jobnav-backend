package com.jobnav.api.security.service.impl;

import com.jobnav.api.feature.user.repository.UserRepository;
import com.jobnav.api.feature.user.repository.entity.QUser;
import com.jobnav.api.feature.user.repository.entity.User;
import com.jobnav.api.feature.user.repository.entity.UserTypeEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SocialOAuth2UserService extends DefaultOAuth2UserService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User loadUser(final OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        return createUpdateSocialUser(oAuth2User);
    }

    private User createUpdateSocialUser(final OAuth2User oAuth2User) {
        final String emailOrId = (String) oAuth2User.getAttributes().getOrDefault("email", oAuth2User.getAttributes().getOrDefault("id", "") + "@gmail.com");
        final String userName = (String) oAuth2User.getAttributes().getOrDefault("given_name", oAuth2User.getAttributes().getOrDefault("id", ""));
        return userRepository.findOne(QUser.user.email.eq(emailOrId))
                .map(updateUser -> userRepository.save(buildUser(updateUser, emailOrId, userName)))
                .orElseGet(() -> {
                    final User saveUser = new User();
                    userRepository.save(buildUser(saveUser, emailOrId, userName));
                    return saveUser;
                });
    }

    private User buildUser(final User user, final String emailOrId, String userName) {
        user.setUsername(userName);
        user.setEmail(emailOrId);
        user.setUserType(UserTypeEnum.APPLICANT.name());
        user.setPassword(passwordEncoder.encode(RandomStringUtils.random(12)));
        return user;
    }
}
