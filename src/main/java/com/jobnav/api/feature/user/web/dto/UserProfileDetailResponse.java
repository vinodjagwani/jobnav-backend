package com.jobnav.api.feature.user.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "JobApplicantProfileDetailResponse", description = "JobApplicantProfileDetailResponse contains response fields for job applicant profile")
public class UserProfileDetailResponse {

    @ApiModelProperty(position = 1, name = "userId", example = "e3204a48-04bf-433c-a88e-66e989915272", required = true)
    String userId;

    @ApiModelProperty(position = 2, name = "username", example = "testAB", required = true)
    String username;

    @ApiModelProperty(position = 3, name = "email", example = "testAB@gmail.com", required = true)
    String email;


}
