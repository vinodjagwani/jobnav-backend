package com.jobnav.api.feature.jobmailing.repository;

import com.jobnav.api.feature.jobmailing.repository.entity.JobMailing;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobMailingRepository extends PagingAndSortingRepository<JobMailing, String>, QuerydslPredicateExecutor<JobMailing> {
}
