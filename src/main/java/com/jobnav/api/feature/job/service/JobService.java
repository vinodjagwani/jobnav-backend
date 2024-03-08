package com.jobnav.api.feature.job.service;

import com.jobnav.api.feature.job.repository.entity.Job;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {

    Job save(final Job job);

    Page<Job> findAll(final Predicate predicate, final Pageable pageable);
}
