package com.jobnav.api.feature.job.web.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "JobQueryResponse", description = "JobQueryResponse contains response fields for querying jobs")
public class JobQueryResponse {

    @ApiModelProperty(position = 1, name = "jobId", example = "123e4567-e89b-42d3-a456-556642440000")
    String jobId;

    @ApiModelProperty(position = 2, name = "title", example = "Developer")
    String title;

    @ApiModelProperty(position = 3, name = "company", example = "ABC")
    String company;

    @ApiModelProperty(position = 4, name = "description", example = "test")
    String description;

    @ApiModelProperty(position = 5, name = "position", example = "testAB")
    String position;

    @ApiModelProperty(position = 6, name = "location", example = "UK")
    String location;

    @ApiModelProperty(position = 7, name = "jobType", example = "Contract")
    String jobType;

    @ApiModelProperty(position = 8, name = "careerPath", example = "testAB")
    String careerPath;

    @ApiModelProperty(position = 9, name = "skills", example = "['JAVA']")
    List<String> skills = Lists.newArrayList();

}
