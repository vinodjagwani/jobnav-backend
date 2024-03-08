package com.jobnav.api.feature.user.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "JobApplicantContactFormCreateRequest", description = "JobApplicantContactFormCreateRequest contains request fields for job applicant contact form")
public class JobApplicantContactFormCreateRequest {

    @NotEmpty(message = "firstName can't be null or empty")
    @ApiModelProperty(position = 1, name = "firstName", example = "testAB", required = true, notes = "email is required when username not available")
    String firstName;

    @NotEmpty(message = "lastName can't be null or empty")
    @ApiModelProperty(position = 2, name = "lastName", example = "testAB", required = true, notes = "email is required when username not available")
    String lastName;

    @Email(message = "email has invalid format")
    @NotEmpty(message = "email can't be null or empty")
    @ApiModelProperty(position = 3, name = "email", example = "testAB", required = true, notes = "email is required when username not available")
    String email;

    @NotEmpty(message = "phone can't be null or empty")
    @ApiModelProperty(position = 4, name = "phone", example = "testAB", required = true, notes = "email is required when username not available")
    String phone;

    @NotEmpty(message = "message can't be null or empty")
    @ApiModelProperty(position = 5, name = "message", example = "testAB", required = true, notes = "email is required when username not available")
    String message;

}
