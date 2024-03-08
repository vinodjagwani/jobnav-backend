package com.jobnav.api.feature.job.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "JobSubmissionCreateRequest", description = "JobSubmissionCreateRequest contains request fields for create job submission")
public class JobSubmissionCreateRequest {

    @NotEmpty(message = "jobId can't be null or empty")
    @ApiModelProperty(position = 1, name = "jobId", example = "123e4567-e89b-42d3-a456-556642440000")
    String jobId;

    @NotEmpty(message = "applicantId can't be null or empty")
    @ApiModelProperty(position = 2, name = "applicantId", example = "123e4567-e89b-42d3-a456-556642440000")
    String applicantId;
}
