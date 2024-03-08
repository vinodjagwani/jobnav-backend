package com.jobnav.api.feature.job.web.facade;


import com.jobnav.api.feature.job.repository.entity.Job;
import com.jobnav.api.feature.job.service.JobService;
import com.jobnav.api.feature.job.web.dto.JobQueryResponse;
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
public class JobQueryFacade {

    JobService jobService;

    public Page<JobQueryResponse> queryAllJobs(final Predicate predicate, final Pageable pageable) {
        log.debug("Start calling querying all jobs with predicate [{}] and pageAble [{}]", predicate, pageable);
        final Page<Job> result = jobService.findAll(predicate, pageable);
        final Page<JobQueryResponse> response = result.map(this::buildJobQueryResponse);
        log.debug("Finish calling querying all jobs with no of records [{}]", response.getTotalElements());
        return response;
    }

    private JobQueryResponse buildJobQueryResponse(final Job job) {
        final JobQueryResponse response = new JobQueryResponse();
        response.setJobId(job.getJobId());
        response.setTitle(job.getTitle());
        response.setCareerPath(job.getCareerPath());
        response.setJobType(job.getJobType());
        response.setDescription(job.getDescription());
        response.setCompany(job.getCompany());
        response.setPosition(job.getPosition());
        response.setLocation(job.getLocation());
        response.setSkills(job.getSkills());
        return response;
    }
}
