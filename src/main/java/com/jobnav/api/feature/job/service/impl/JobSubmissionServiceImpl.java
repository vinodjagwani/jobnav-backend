package com.jobnav.api.feature.job.service.impl;


import com.jobnav.api.feature.job.repository.JobSubmissionRepository;
import com.jobnav.api.feature.job.repository.entity.JobSubmission;
import com.jobnav.api.feature.job.service.JobSubmissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JobSubmissionServiceImpl implements JobSubmissionService {

    JobSubmissionRepository jobSubmissionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobSubmission save(final JobSubmission jobSubmission) {
        log.debug("Start saving job submitted with jobSubmissionId [{}]", jobSubmission.getJobSubmittedId());
        final JobSubmission savedJobSubmission = jobSubmissionRepository.save(jobSubmission);
        log.debug("Finish saving job submitted with jobSubmissionId [{}]", savedJobSubmission.getJobSubmittedId());
        return savedJobSubmission;
    }

}
