package com.jobnav.api.feature.job.web.facade;


import com.jobnav.api.feature.job.repository.entity.Job;
import com.jobnav.api.feature.job.service.JobService;
import com.jobnav.api.feature.job.web.dto.JobCreateRequest;
import com.jobnav.api.feature.job.web.dto.JobCreateResponse;
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
public class JobCreateUpdateFacade {

    JobService jobService;

    @Transactional(rollbackFor = Exception.class)
    public JobCreateResponse createJob(final JobCreateRequest request) {
        log.debug("Start calling saving job with request [{}]", request);
        final JobCreateResponse response = new JobCreateResponse();
        final Job savedJob = jobService.save(buildJob(request));
        response.setJobId(savedJob.getJobId());
        log.debug("Finish calling saving job with jobId [{}]", response.getJobId());
        return response;
    }

    private Job buildJob(final JobCreateRequest request) {
        final Job job = new Job();
        job.setPosition(request.getPosition());
        job.setJobType(request.getJobType());
        job.setLocation(request.getLocation());
        job.setCompany(request.getCompany());
        job.setDescription(request.getDescription());
        job.setSkills(request.getSkills());
        job.setCareerPath(request.getCareerPath());
        job.setTitle(request.getTitle());
        return job;
    }

}
