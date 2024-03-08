package com.jobnav.api.feature.user.web.facade;


import com.jobnav.api.feature.user.repository.entity.UserSubscription;
import com.jobnav.api.feature.user.service.UserSubscriptionService;
import com.jobnav.api.feature.user.web.dto.UserSubscriptionCreateRequest;
import com.jobnav.api.feature.user.web.dto.UserSubscriptionCreateResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSubscriptionCreateFacade {

    UserSubscriptionService userSubscriptionService;

    public UserSubscriptionCreateResponse createUserSubscription(final UserSubscriptionCreateRequest request) {
        final UserSubscriptionCreateResponse response = new UserSubscriptionCreateResponse();
        final UserSubscription userSubscription = userSubscriptionService.save(buildUserSubscription(request));
        response.setUserSubscriptionId(userSubscription.getUserSubscriptionId());
        return response;
    }

    private UserSubscription buildUserSubscription(final UserSubscriptionCreateRequest request) {
        final UserSubscription userSubscription = new UserSubscription();
        userSubscription.setUserId(request.getUserId());
        userSubscription.setActive(Boolean.TRUE);
        userSubscription.setPlan(request.getPlan());
        userSubscription.setAmount(request.getAmount());
        userSubscription.setStartDateTime(LocalDateTime.now());
        userSubscription.setEndDateTime(LocalDateTime.now().plusDays(30));
        return userSubscription;
    }
}
