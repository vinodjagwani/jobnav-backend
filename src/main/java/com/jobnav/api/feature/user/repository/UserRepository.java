package com.jobnav.api.feature.user.repository;

import com.jobnav.api.feature.user.repository.entity.User;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, String>, QuerydslPredicateExecutor<User> {
}
