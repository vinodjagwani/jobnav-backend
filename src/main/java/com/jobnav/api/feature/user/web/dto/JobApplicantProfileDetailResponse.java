package com.jobnav.api.feature.user.web.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "JobApplicantProfileDetailResponse", description = "JobApplicantProfileDetailResponse contains response fields for job applicant profile detail")
public class JobApplicantProfileDetailResponse {

    @ApiModelProperty(position = 1, name = "jobApplicantProfileId", example = "02a4c890-f2e9-440d-a55f-9148bce0f0c1")
    String jobApplicantProfileId;

    @ApiModelProperty(position = 2, name = "firstName", example = "testAB")
    String firstName;

    @ApiModelProperty(position = 3, name = "lastName", example = "Abcdef")
    String lastName;

    @ApiModelProperty(position = 4, name = "email", example = "testAB@gmail.com")
    String email;

    @ApiModelProperty(position = 5, name = "language", example = "EN")
    String language;

    @ApiModelProperty(position = 6, name = "mobile", example = "23423")
    String mobile;

    @ApiModelProperty(position = 7, name = "photo", example = "02a4c890")
    String photo;

    @ApiModelProperty(position = 8, name = "location", example = "UK")
    String location;

    @ApiModelProperty(position = 9, name = "skills", example = "['JAVA']")
    List<String> skills = Lists.newArrayList();

    @ApiModelProperty(position = 10, name = "workExperience", example = "4")
    int workExperience;

    @ApiModelProperty(position = 11, name = "position", example = "Software Engineer")
    String position;

    @ApiModelProperty(position = 12, name = "visaRequiredCurrent", example = "true")
    boolean visaRequiredCurrent;

    @ApiModelProperty(position = 13, name = "visaRequiredFuture", example = "true")
    boolean visaRequiredFuture;

}
