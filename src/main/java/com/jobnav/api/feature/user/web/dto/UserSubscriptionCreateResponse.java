package com.jobnav.api.feature.user.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "UserSubscriptionCreateResponse", description = "UserSubscriptionCreateResponse contains response fields for user subscription")
public class UserSubscriptionCreateResponse {

    @ApiModelProperty(position = 1, name = "userSubscriptionId", example = "d73aae91-4737-475a-b7f7-58af3513fd31")
    String userSubscriptionId;

}
