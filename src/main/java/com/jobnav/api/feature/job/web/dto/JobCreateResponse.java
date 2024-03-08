package com.jobnav.api.feature.job.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "JobCreateResponse", description = "JobCreateResponse contains response fields for create job")
public class JobCreateResponse {

    @ApiModelProperty(position = 1, name = "jobId", example = "123e4567-e89b-42d3-a456-556642440000")
    String jobId;
}
