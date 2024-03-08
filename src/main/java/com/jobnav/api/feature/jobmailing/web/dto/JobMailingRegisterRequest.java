package com.jobnav.api.feature.jobmailing.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "JobMailingRegisterRequest", description = "JobMailingRegisterRequest contains request fields for register job mailing")
public class JobMailingRegisterRequest {

    @Email(message = "email has a invalid format")
    @NotEmpty(message = "email can't be null or empty")
    @ApiModelProperty(position = 1, name = "email", example = "testAB@gmail.com", required = true)
    String email;

}
