package com.jobnav.api.feature.job.repository;

import com.jobnav.api.feature.job.repository.entity.Job;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobRepository extends PagingAndSortingRepository<Job, String>, QuerydslPredicateExecutor<Job> {
}
