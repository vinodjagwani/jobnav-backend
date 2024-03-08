package com.jobnav.api.feature.user.web.controller;

import com.jobnav.api.feature.user.web.dto.UserSubscriptionCreateRequest;
import com.jobnav.api.feature.user.web.dto.UserSubscriptionCreateResponse;
import com.jobnav.api.feature.user.web.facade.UserSubscriptionCreateFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscriptions")
@Api(tags = "User Subscriptions", value = "/")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserSubscriptionController {

    UserSubscriptionCreateFacade userSubscriptionCreateFacade;

    @ApiOperation(value = "User Subscription", notes = "This api is used for creating user subscription")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSubscriptionCreateResponse> createUserSubscription(@Valid @RequestBody final UserSubscriptionCreateRequest request) {
        return new ResponseEntity<>(userSubscriptionCreateFacade.createUserSubscription(request), HttpStatus.CREATED);
    }

}
