package com.jobnav.api.feature.user.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "UserLoginResponse", description = "UserLoginResponse contains response fields for user")
public class UserLoginResponse {

    @ApiModelProperty(position = 1, name = "userId", example = "02a4c890-f2e9-440d-a55f-9148bce0f0c1")
    String userId;

    @ApiModelProperty(position = 2, name = "username", example = "testAB")
    String username;

    @ApiModelProperty(position = 3, name = "email", example = "testAB@gmail.com")
    String email;

    @ApiModelProperty(position = 4, name = "accessToken", example = "02a4c890-f2e9-440d-a55f-9148bce0f0c1")
    String accessToken;

    @ApiModelProperty(position = 5, name = "tokenType", example = "Bearer")
    String tokenType;

    @ApiModelProperty(position = 6, name = "userType", example = "COMPANY")
    String userType;

}
