package com.jobnav.api.feature.job.service.impl;

import com.jobnav.api.feature.job.repository.JobRepository;
import com.jobnav.api.feature.job.repository.entity.Job;
import com.jobnav.api.feature.job.service.JobService;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JobServiceImpl implements JobService {

    JobRepository jobRepository;

    @Override
    public Job save(final Job job) {
        log.debug("Start saving job with jobId [{}]", job.getJobId());
        final Job savedJob = jobRepository.save(job);
        log.debug("Finish saving job with jobId [{}]", savedJob.getJobId());
        return savedJob;
    }

    @Override
    public Page<Job> findAll(final Predicate predicate, final Pageable pageable) {
        log.debug("Start querying all jobs with predicate [{}] and pageAble [{}]", predicate, pageable);
        final Page<Job> result = jobRepository.findAll(predicate, pageable);
        log.debug("Finish querying all jobs with total no of records [{}]", result.getTotalElements());
        return result;
    }
}
