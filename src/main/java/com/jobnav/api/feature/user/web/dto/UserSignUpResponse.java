package com.jobnav.api.feature.user.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "UserSignUpResponse", description = "UserSignUpResponse contains response fields for signUp user")
public class UserSignUpResponse {

    @ApiModelProperty(position = 1, name = "userId", example = "d73aae91-4737-475a-b7f7-58af3513fd31")
    String userId;

    @ApiModelProperty(position = 2, name = "username", example = "testAB")
    String username;

    @ApiModelProperty(position = 3, name = "email", example = "testAB@gmail.com")
    String email;

    @ApiModelProperty(position = 4, name = "userType", example = "COMPANY")
    String userType;
}
