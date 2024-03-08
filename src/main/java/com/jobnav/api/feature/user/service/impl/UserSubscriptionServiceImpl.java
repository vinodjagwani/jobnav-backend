package com.jobnav.api.feature.user.service.impl;

import com.jobnav.api.feature.user.repository.UserSubscriptionRepository;
import com.jobnav.api.feature.user.repository.entity.UserSubscription;
import com.jobnav.api.feature.user.service.UserSubscriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

    UserSubscriptionRepository userSubscriptionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserSubscription save(final UserSubscription subscription) {
        log.debug("Start saving userSubscription with userSubscriptionId [{}]", subscription.getUserSubscriptionId());
        final UserSubscription userSubscription = userSubscriptionRepository.save(subscription);
        log.debug("End saving userSubscription with userSubscriptionId [{}]", subscription.getUserSubscriptionId());
        return userSubscription;
    }
}
