package com.jobnav.api.feature.job.web.controller;


import com.google.common.collect.Lists;
import com.jobnav.api.feature.job.web.dto.JobQueryResponse;
import com.jobnav.api.feature.job.web.facade.JobCreateUpdateFacade;
import com.jobnav.api.feature.job.web.facade.JobQueryFacade;
import com.jobnav.api.feature.user.repository.UserRepository;
import com.jobnav.api.feature.utils.MockUtils;
import com.jobnav.api.security.CustomAuthenticationSuccessHandler;
import com.jobnav.api.security.TokenIntrospect;
import com.jobnav.api.security.WebSecurityConfig;
import com.jobnav.api.security.service.AuthenticationDetailsService;
import com.querydsl.core.types.Predicate;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Optional.ofNullable;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
@WebMvcTest(controllers = JobController.class,
        excludeFilters = {@ComponentScan.Filter(classes = Configuration.class), @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfig.class, ClientRegistrationRepository.class})},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class})
class JobControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TokenIntrospect tokenIntrospect;

    @MockBean
    JobCreateUpdateFacade jobCreateUpdateFacade;

    @MockBean
    AuthenticationDetailsService authenticationDetailsService;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @MockBean
    JobQueryFacade jobQueryFacade;

    @Test
    @SneakyThrows
    void testGetAllJobs() {
        final JobQueryResponse response = ofNullable(MockUtils.getResource("mock/job-applicant-all-jobs-response.json", JobQueryResponse.class)).orElse(new JobQueryResponse());
        when(jobQueryFacade.queryAllJobs(any(Predicate.class), any(Pageable.class))).thenReturn(new PageImpl<>(Lists.newArrayList(response)));
        mockMvc.perform(get("/api/v1/jobs").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].title", is("test")))
                .andDo(print()).andExpect(status().isOk());
    }
}
