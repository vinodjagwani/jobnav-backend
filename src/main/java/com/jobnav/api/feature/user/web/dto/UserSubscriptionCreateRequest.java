package com.jobnav.api.feature.user.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "UserSubscriptionCreateRequest", description = "UserSubscriptionCreateRequest contains request fields for user subscription")
public class UserSubscriptionCreateRequest {

    @NotEmpty(message = "plan can't be null or empty")
    @ApiModelProperty(position = 1, name = "username", example = "Monthly", required = true)
    String plan;

    @NotNull(message = "amount can't be null or empty")
    @ApiModelProperty(position = 2, name = "amount", example = "23", required = true)
    BigDecimal amount;


    @NotEmpty(message = "userId can't be null or empty")
    @ApiModelProperty(position = 3, name = "userId", example = "d73aae91-4737-475a-b7f7-58af3513fd31", required = true)
    String userId;

}
