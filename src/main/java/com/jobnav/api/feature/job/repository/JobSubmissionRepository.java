package com.jobnav.api.feature.job.repository;

import com.jobnav.api.feature.job.repository.entity.JobSubmission;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobSubmissionRepository extends PagingAndSortingRepository<JobSubmission, String>, QuerydslPredicateExecutor<JobSubmission> {
}
