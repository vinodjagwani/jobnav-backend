package com.jobnav.api.feature.job.web.controller;


import com.jobnav.api.feature.job.repository.entity.Job;
import com.jobnav.api.feature.job.web.dto.JobCreateRequest;
import com.jobnav.api.feature.job.web.dto.JobCreateResponse;
import com.jobnav.api.feature.job.web.dto.JobQueryResponse;
import com.jobnav.api.feature.job.web.facade.JobCreateUpdateFacade;
import com.jobnav.api.feature.job.web.facade.JobQueryFacade;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobs")
@Api(tags = "Jobs", value = "/")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JobController {

    JobQueryFacade jobQueryFacade;

    JobCreateUpdateFacade jobCreateUpdateFacade;

    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Query Job Api", notes = "This api is used for querying jobs")
    public ResponseEntity<Page<JobQueryResponse>> getAllJobs(@QuerydslPredicate(root = Job.class) final Predicate predicate, @PageableDefault(value = 20) final Pageable pageable) {
        return new ResponseEntity<>(jobQueryFacade.queryAllJobs(predicate, pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create Job Api", notes = "This api is used for create job")
    public ResponseEntity<JobCreateResponse> createJob(@Valid @RequestBody final JobCreateRequest request) {
        return new ResponseEntity<>(jobCreateUpdateFacade.createJob(request), HttpStatus.CREATED);
    }
}
