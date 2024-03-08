package com.jobnav.api.feature.user.web.controller;

import com.jobnav.api.feature.user.web.dto.UserLoginRequest;
import com.jobnav.api.feature.user.web.dto.UserLoginResponse;
import com.jobnav.api.feature.user.web.facade.UserLoginFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Api(tags = "User", value = "/")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserLoginController {

    UserLoginFacade userLoginService;

    @ApiOperation(value = "User Login Api", notes = "This api is used for login user")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponse> loginUser(@Valid @RequestBody final UserLoginRequest request) {
        return new ResponseEntity<>(userLoginService.loginUser(request), HttpStatus.OK);
    }

    @GetMapping(value = "/social-login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponse> socialLogin(@RequestParam("access_token") String accessToken) {
        return new ResponseEntity<>(userLoginService.loginUser(accessToken), HttpStatus.OK);
    }


}
