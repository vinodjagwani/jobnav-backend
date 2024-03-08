package com.jobnav.api.feature.user.repository;

import com.jobnav.api.feature.user.repository.entity.CompanyProfile;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyProfileRepository extends PagingAndSortingRepository<CompanyProfile, String>, QuerydslPredicateExecutor<CompanyProfile> {
}
