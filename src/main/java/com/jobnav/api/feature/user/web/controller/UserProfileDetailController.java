package com.jobnav.api.feature.user.web.controller;

import com.jobnav.api.feature.user.repository.entity.User;
import com.jobnav.api.feature.user.web.dto.JobApplicantProfileDetailResponse;
import com.jobnav.api.feature.user.web.dto.UserProfileDetailResponse;
import com.jobnav.api.feature.user.web.dto.UserProfileDetailUpdateRequest;
import com.jobnav.api.feature.user.web.facade.UserProfileFacade;
import com.jobnav.api.validator.annotation.FileExtension;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
@Api(tags = "User", value = "/")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserProfileDetailController {

    UserProfileFacade userProfileFacade;

    @PreAuthorize("#userId == principal.attributes.get('userId')")
    @ApiOperation(value = "Get User Profile Detail Api", notes = "This api is used for get detail of user")
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileDetailResponse> getUserProfileById(@PathVariable("userId") final String userId) {
        return new ResponseEntity<>(userProfileFacade.getUserProfileByUserId(userId), HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.attributes.get('userId')")
    @ApiOperation(value = "Update User Profile Detail Api", notes = "This api is used for update profile detail of user")
    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileDetailResponse> updateUserProfile(@PathVariable("userId") final String userId, @Valid @RequestBody @NotNull final UserProfileDetailUpdateRequest request) {
        return new ResponseEntity<>(userProfileFacade.updateUserProfile(userId, request), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_APPLICANT') AND #jobApplicantId == principal.attributes.get('userId')")
    @ApiOperation(value = "Update Job Applicant Resume Api", notes = "This api is used for upload job applicant resume")
    @PostMapping(value = "/{jobApplicantId}/resume-upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> uploadJobApplicantResume(@PathVariable("jobApplicantId") final String jobApplicantId,
                                                         @FileExtension(message = "Invalid file extension") @RequestParam("file") final MultipartFile file) {
        userProfileFacade.uploadJobApplicantResume(jobApplicantId, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_COMPANY') || hasRole('ROLE_RECRUITER') || hasRole('ROLE_HIRING_MANAGER') || hasRole('ROLE_FREELANCER')")
    @ApiOperation(value = "Search Candidate Profiles", notes = "This api is used for searching Candidate profiles")
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<JobApplicantProfileDetailResponse>> searchCandidateProfiles(@QuerydslPredicate(root = User.class) final Predicate predicate, @PageableDefault(value = 20) final Pageable pageable) {
        return new ResponseEntity<>(userProfileFacade.searchCandidateProfiles(predicate, pageable), HttpStatus.OK);
    }

}
