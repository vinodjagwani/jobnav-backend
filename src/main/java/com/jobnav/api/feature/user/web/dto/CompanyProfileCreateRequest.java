package com.jobnav.api.feature.user.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "CompanyProfileCreateRequest", description = "CompanyProfileCreateRequest contains request fields for create company profile")
public class CompanyProfileCreateRequest {

    @NotEmpty(message = "aboutCompany can't be null or empty")
    @ApiModelProperty(position = 1, name = "aboutCompany", example = "testAB", required = true)
    String aboutCompany;

    @ApiModelProperty(position = 2, name = "companyLogo", example = "31243241")
    String companyLogo;
}
