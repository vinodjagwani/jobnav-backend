package com.jobnav.api.feature.user.web.controller;


import com.jobnav.api.feature.user.web.dto.UserSignUpRequest;
import com.jobnav.api.feature.user.web.dto.UserSignUpResponse;
import com.jobnav.api.feature.user.web.facade.UserSignUpFacade;
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
@RequestMapping("/api/v1/auth")
@Api(tags = "User", value = "/")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserSignUpController {

    UserSignUpFacade userSignUpFacade;

    @ApiOperation(value = "User Sign up", notes = "This api is used for sign up user")
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSignUpResponse> signUpUser(@Valid @RequestBody final UserSignUpRequest request) {
        return new ResponseEntity<>(userSignUpFacade.signUpUser(request), HttpStatus.CREATED);
    }

}
