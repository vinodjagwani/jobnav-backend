package com.jobnav.api.feature.user.web.controller;

import com.jobnav.api.feature.user.web.dto.JobApplicantContactFormCreateRequest;
import com.jobnav.api.feature.user.web.dto.JobApplicantContactFormCreateResponse;
import com.jobnav.api.feature.user.web.facade.JobApplicantContactFormFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contact-form")
@Api(tags = "Job Applicant Contact Form", value = "/")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JobApplicantContactFormController {

    JobApplicantContactFormFacade jobApplicantContactFormFacade;

    @ApiOperation(value = "Job Applicant Contact Form Api", notes = "This api is used for create contact form for job applicant")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobApplicantContactFormCreateResponse> createJobApplicantContactForm(@Valid @RequestBody final JobApplicantContactFormCreateRequest request) {
        return new ResponseEntity<>(jobApplicantContactFormFacade.createJobApplicantContactForm(request), HttpStatus.OK);
    }
}
