package com.jobnav.api.feature.user.web.controller;

import com.jobnav.api.feature.user.repository.UserRepository;
import com.jobnav.api.feature.user.web.dto.JobApplicantContactFormCreateRequest;
import com.jobnav.api.feature.user.web.dto.JobApplicantContactFormCreateResponse;
import com.jobnav.api.feature.user.web.facade.JobApplicantContactFormFacade;
import com.jobnav.api.feature.utils.MockUtils;
import com.jobnav.api.security.CustomAuthenticationSuccessHandler;
import com.jobnav.api.security.TokenIntrospect;
import com.jobnav.api.security.service.AuthenticationDetailsService;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@WebMvcTest(controllers = JobApplicantContactFormController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class})
class UserContactFormControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TokenIntrospect tokenIntrospect;

    @MockBean
    AuthenticationDetailsService authenticationDetailsService;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    JobApplicantContactFormFacade jobApplicantContactFormFacade;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Test
    @SneakyThrows
    void testCreateJobApplicantContactForm() {
        final String request = ofNullable(MockUtils.getResource("mock/job-applicant-contact-form-request.json", String.class)).orElse("");
        final JobApplicantContactFormCreateResponse response = ofNullable(MockUtils.getResource("mock/job-applicant-contact-form-response.json", JobApplicantContactFormCreateResponse.class))
                .orElse(new JobApplicantContactFormCreateResponse());
        when(jobApplicantContactFormFacade.createJobApplicantContactForm(any(JobApplicantContactFormCreateRequest.class))).thenReturn(response);
        mockMvc.perform(post("/api/v1/contact-form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(jsonPath("$.jobApplicantContactFormId", is("1daf2218-842c-422e-a8d1-52b8ff318fa4")))
                .andDo(print()).andExpect(status().isOk());
    }
}
