package com.jobnav.api.feature.user.web.controller;


import com.jobnav.api.feature.user.web.dto.CompanyProfileCreateRequest;
import com.jobnav.api.feature.user.web.dto.CompanyProfileCreateResponse;
import com.jobnav.api.feature.user.web.facade.CompanyProfileFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile/company")
@Api(tags = "Company Profile", value = "/")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CompanyProfileController {

    CompanyProfileFacade companyProfileFacade;

    @PreAuthorize("#companyUserId == principal.attributes.get('userId')")
    @ApiOperation(value = "Update Company Profile Detail Api", notes = "This api is used for update profile detail of company")
    @PutMapping(value = "/{companyUserId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyProfileCreateResponse> updateCompanyProfile(@PathVariable("companyUserId") final String companyUserId, @Valid @RequestBody @NotNull final CompanyProfileCreateRequest request) {
        return new ResponseEntity<>(companyProfileFacade.createUpdateCompanyProfile(companyUserId, request), HttpStatus.OK);
    }
}
