package com.jobnav.api.feature.job.web.facade;

import com.jobnav.api.feature.job.repository.entity.JobSubmission;
import com.jobnav.api.feature.job.service.JobSubmissionService;
import com.jobnav.api.feature.job.web.dto.JobSubmissionCreateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JobSubmissionCreateFacade {

    JobSubmissionService jobSubmissionService;

    public void applyForJob(final JobSubmissionCreateRequest request) {
        log.debug("Start calling saving job submission with request [{}]", request);
        final JobSubmission jobSubmission = jobSubmissionService.save(buildJobSubmission(request));
        log.debug("Finish calling saving job submission with jobSubmissionId [{}]", jobSubmission.getJobSubmittedId());
    }

    private JobSubmission buildJobSubmission(final JobSubmissionCreateRequest request) {
        final JobSubmission jobSubmission = new JobSubmission();
        jobSubmission.setJobId(request.getJobId());
        jobSubmission.setApplicantId(request.getApplicantId());
        return jobSubmission;
    }

}
