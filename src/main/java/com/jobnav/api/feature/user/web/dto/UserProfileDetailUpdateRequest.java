package com.jobnav.api.feature.user.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "UserProfileDetailUpdateRequest", description = "UserProfileDetailUpdateRequest contains request fields for user profile detail")
public class UserProfileDetailUpdateRequest {

    @ApiModelProperty(position = 1, name = "firstName", example = "testAB", required = true)
    String firstName;

    @ApiModelProperty(position = 2, name = "lastName", example = "testAB", required = true)
    String lastName;

    @ApiModelProperty(position = 3, name = "mobile", example = "343534", required = true)
    String mobile;

    @Email(message = "email has invalid format")
    @ApiModelProperty(position = 4, name = "email", example = "testAB@gmail.com", required = true)
    String email;

    @ApiModelProperty(position = 5, name = "language", example = "EN")
    String language;

    @ApiModelProperty(position = 6, name = "location", example = "PK")
    String location;

    @ApiModelProperty(position = 7, name = "skills", example = "['java', 'spring']")
    List<String> skills;

    @ApiModelProperty(position = 8, name = "workExperience", example = "2")
    int workExperience;

    @ApiModelProperty(position = 9, name = "positionTitle", example = "Accountant")
    String positionTitle;

    @ApiModelProperty(position = 10, name = "startDate", example = "2020-12-12")
    LocalDate startDate;

    @ApiModelProperty(position = 11, name = "isCurrentEmployed", example = "false")
    boolean isCurrentEmployed;

    @ApiModelProperty(position = 12, name = "isAvailable", example = "true")
    boolean isAvailable;

    @ApiModelProperty(position = 13, name = "isRemote", example = "false")
    boolean isRemote;

    @ApiModelProperty(position = 14, name = "visaRequiredCurrent", example = "true")
    boolean visaRequiredCurrent;

    @ApiModelProperty(position = 15, name = "visaRequiredFuture", example = "true")
    boolean visaRequiredFuture;

}
