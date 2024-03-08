package com.jobnav.api.feature.jobmailing.web.dto;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "JobMailingRegisterResponse", description = "JobMailingRegisterResponse contains response fields register for job mailing")
public class JobMailingRegisterResponse {
}
