package com.jobnav.api.feature.user.web.dto;

import com.jobnav.api.feature.user.repository.entity.UserTypeEnum;
import com.jobnav.api.validator.annotation.Enum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "UserLoginRequest", description = "UserLoginRequest contains request fields for user")
public class UserLoginRequest {

    @ApiModelProperty(position = 1, name = "username", example = "testAB", required = true)
    String username;

    @Email(message = "email has invalid format")
    @ApiModelProperty(position = 2, name = "email", example = "testAB", required = true, notes = "email is required when username not available")
    String email;

    @NotEmpty(message = "password can't be null or empty")
    @ApiModelProperty(position = 3, name = "password", example = "12345", required = true, notes = "username is required when email not available")
    String password;

    @NotEmpty(message = "userType can't be null or empty")
    @Enum(enumClass = UserTypeEnum.class, message = "userType has invalid values")
    @ApiModelProperty(position = 4, name = "userType", example = "COMPANY", required = true)
    String userType;

}
