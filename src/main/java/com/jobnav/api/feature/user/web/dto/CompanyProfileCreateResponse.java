package com.jobnav.api.feature.user.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "CompanyProfileCreateResponse", description = "CompanyProfileCreateResponse contains response fields for create company profile")
public class CompanyProfileCreateResponse {

    @ApiModelProperty(position = 1, name = "companyProfileId", example = "testAB")
    String companyProfileId;

}
