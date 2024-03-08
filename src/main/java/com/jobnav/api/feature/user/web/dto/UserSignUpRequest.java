package com.jobnav.api.feature.user.web.dto;

import com.jobnav.api.feature.user.repository.entity.UserTypeEnum;
import com.jobnav.api.validator.annotation.Enum;
import com.jobnav.api.validator.annotation.ValidHiringManager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ValidHiringManager(message = "companyId is required when userType HIRING_MANAGER")
@ApiModel(value = "UserSignUpRequest", description = "UserSignUpRequest contains request fields for signUp job user")
public class UserSignUpRequest {

    @NotEmpty(message = "username can't be null or empty")
    @ApiModelProperty(position = 1, name = "username", example = "testAB", required = true)
    String username;

    @Email(message = "email has invalid format")
    @NotEmpty(message = "email can't be null or empty")
    @ApiModelProperty(position = 2, name = "email", example = "testAB@gmail.com", required = true)
    String email;

    @NotEmpty(message = "password can't be null or empty")
    @ApiModelProperty(position = 3, name = "password", example = "1234", required = true)
    String password;

    @NotEmpty(message = "userType can't be null or empty")
    @Enum(enumClass = UserTypeEnum.class, message = "userType has invalid values")
    @ApiModelProperty(position = 4, name = "userType", example = "COMPANY", required = true)
    String userType;

    @ApiModelProperty(position = 5, name = "companyId", example = "02a4c890-f2e9-440d-a55f-9148bce0f0c1", notes = "Required only when creating hiring manager")
    String companyId;


}
