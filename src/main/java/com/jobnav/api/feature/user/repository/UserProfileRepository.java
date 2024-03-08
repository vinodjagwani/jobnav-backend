package com.jobnav.api.feature.user.repository;

import com.jobnav.api.feature.user.repository.entity.UserProfile;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserProfileRepository extends PagingAndSortingRepository<UserProfile, String>, QuerydslPredicateExecutor<UserProfile> {
}
