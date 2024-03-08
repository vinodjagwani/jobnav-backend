package com.jobnav.api.feature.user.web.controller;

import com.jobnav.api.feature.user.repository.UserRepository;
import com.jobnav.api.feature.user.web.dto.UserLoginRequest;
import com.jobnav.api.feature.user.web.dto.UserLoginResponse;
import com.jobnav.api.feature.user.web.facade.UserLoginFacade;
import com.jobnav.api.feature.utils.MockUtils;
import com.jobnav.api.security.CustomAuthenticationSuccessHandler;
import com.jobnav.api.security.TokenIntrospect;
import com.jobnav.api.security.service.AuthenticationDetailsService;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static java.util.Optional.ofNullable;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
@WebMvcTest(controllers = UserLoginController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class})
class UserLoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TokenIntrospect tokenIntrospect;

    @MockBean
    AuthenticationDetailsService authenticationDetailsService;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    UserLoginFacade userLoginService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Test
    @SneakyThrows
    void testLoginUser() {
        final String request = ofNullable(MockUtils.getResource("mock/job-applicant-login-request.json", String.class)).orElse("");
        final UserLoginResponse response = buildUserLoginResponse();
        when(userLoginService.loginUser(any(UserLoginRequest.class))).thenReturn(response);
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(jsonPath("$.email", is("test@gmail.com")))
                .andDo(print()).andExpect(status().isOk());
    }

    private UserLoginResponse buildUserLoginResponse() {
        return UserLoginResponse.builder()
                .email("test@gmail.com")
                .userId(UUID.randomUUID().toString())
                .username("vinod")
                .tokenType("Bearer")
                .build();
    }

}
