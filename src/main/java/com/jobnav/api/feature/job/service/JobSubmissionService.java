package com.jobnav.api.feature.job.service;


import com.jobnav.api.feature.job.repository.entity.JobSubmission;

public interface JobSubmissionService {

    JobSubmission save(final JobSubmission jobSubmission);

}
