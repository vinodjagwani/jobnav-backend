package com.jobnav.api.feature.user.repository;

import com.jobnav.api.feature.user.repository.entity.JobApplicantContactForm;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobApplicationContactFormRepository extends PagingAndSortingRepository<JobApplicantContactForm, String>, QuerydslPredicateExecutor<JobApplicantContactForm> {
}
