package com.jobnav.api.feature.user.web.controller;


import com.jobnav.api.feature.user.repository.UserRepository;
import com.jobnav.api.feature.user.web.dto.UserSignUpRequest;
import com.jobnav.api.feature.user.web.dto.UserSignUpResponse;
import com.jobnav.api.feature.user.web.facade.UserSignUpFacade;
import com.jobnav.api.feature.utils.MockUtils;
import com.jobnav.api.security.CustomAuthenticationSuccessHandler;
import com.jobnav.api.security.TokenIntrospect;
import com.jobnav.api.security.WebSecurityConfig;
import com.jobnav.api.security.service.AuthenticationDetailsService;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Optional.ofNullable;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
@WebMvcTest(controllers = UserSignUpController.class,
        excludeFilters = {@ComponentScan.Filter(classes = Configuration.class), @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfig.class})},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class})
class UserSignUpControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TokenIntrospect tokenIntrospect;

    @MockBean
    AuthenticationDetailsService authenticationDetailsService;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    UserSignUpFacade userSignUpFacade;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Test
    @SneakyThrows
    void testSignUpJobApplicant() {
        final String request = ofNullable(MockUtils.getResource("mock/job-applicant-signup-request.json", String.class))
                .orElse("");
        final UserSignUpResponse response = ofNullable(MockUtils.getResource("mock/job-applicant-signup-response.json", UserSignUpResponse.class))
                .orElse(new UserSignUpResponse());
        when(userSignUpFacade.signUpUser(any(UserSignUpRequest.class))).thenReturn(response);
        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(jsonPath("$.email", is("testAB@gmail.com")))
                .andDo(print()).andExpect(status().isCreated());
    }

}
