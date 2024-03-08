package com.jobnav.api.feature.user.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "JobApplicantContactFormCreateResponse", description = "JobApplicantContactFormCreateResponse contains response fields for job applicant contact form")
public class JobApplicantContactFormCreateResponse {

    @NotEmpty(message = "jobApplicantContactFormId can't be null or empty")
    @ApiModelProperty(position = 1, name = "jobApplicantContactFormId", example = "1daf2218-842c-422e-a8d1-52b8ff318fa4", required = true)
    String jobApplicantContactFormId;

}
