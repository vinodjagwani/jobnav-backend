package com.jobnav.api.feature.jobmailing.web.controller;

import com.jobnav.api.feature.jobmailing.web.dto.JobMailingRegisterRequest;
import com.jobnav.api.feature.jobmailing.web.facade.JobMailingRegisterFacade;
import com.jobnav.api.feature.user.repository.UserRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
@WebMvcTest(controllers = JobMailingRegisterController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class})
class JobMailingRegisterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TokenIntrospect tokenIntrospect;

    @MockBean
    AuthenticationDetailsService authenticationDetailsService;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @MockBean
    JobMailingRegisterFacade jobMailingRegisterFacade;

    @Test
    @SneakyThrows
    void testRegisterJobMailing() {
        final String request = ofNullable(MockUtils.getResource("mock/job-applicant-mailing-create-request.json", String.class)).orElse("");
        doNothing().when(jobMailingRegisterFacade).registerJobMail(any(JobMailingRegisterRequest.class));
        mockMvc.perform(post("/api/v1/job-mailing/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print()).andExpect(status().isOk());
    }

}
