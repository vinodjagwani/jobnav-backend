package com.jobnav.api.feature.user.repository;

import com.jobnav.api.feature.user.repository.entity.UserSubscription;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserSubscriptionRepository extends PagingAndSortingRepository<UserSubscription, String>, QuerydslPredicateExecutor<UserSubscription> {

}
