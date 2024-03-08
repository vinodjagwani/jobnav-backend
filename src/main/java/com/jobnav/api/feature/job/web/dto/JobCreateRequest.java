package com.jobnav.api.feature.job.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "JobCreateRequest", description = "JobCreateRequest contains request fields for create job")
public class JobCreateRequest {


    @NotEmpty(message = "")
    @ApiModelProperty(position = 1, name = "title", example = "Developer", required = true)
    String title;

    @ApiModelProperty(position = 2, name = "company", example = "ABC", required = true)
    String company;

    @ApiModelProperty(position = 3, name = "description", example = "test", required = true)
    String description;

    @ApiModelProperty(position = 4, name = "position", example = "testAB", required = true)
    String position;

    @ApiModelProperty(position = 5, name = "location", example = "UK", required = true)
    String location;

    @ApiModelProperty(position = 6, name = "jobType", example = "Contract", required = true)
    String jobType;

    @ApiModelProperty(position = 7, name = "careerPath", example = "testAB", required = true)
    String careerPath;

    @ApiModelProperty(position = 8, name = "careerPath", example = "['JAVA']", required = true)
    List<String> skills;

}
