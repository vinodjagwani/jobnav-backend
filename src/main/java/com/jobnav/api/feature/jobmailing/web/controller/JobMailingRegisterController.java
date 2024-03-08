package com.jobnav.api.feature.jobmailing.web.controller;

import com.jobnav.api.feature.jobmailing.web.dto.JobMailingRegisterRequest;
import com.jobnav.api.feature.jobmailing.web.facade.JobMailingRegisterFacade;
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
@RequestMapping("/api/v1/job-mailing")
@Api(tags = "Job Mailing", value = "/")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JobMailingRegisterController {

    JobMailingRegisterFacade jobMailingRegisterFacade;

    @ApiOperation(value = "Job Mailing Register Api", notes = "This api is used for job mailing register")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerJobMailing(@Valid @RequestBody final JobMailingRegisterRequest request) {
        jobMailingRegisterFacade.registerJobMail(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
