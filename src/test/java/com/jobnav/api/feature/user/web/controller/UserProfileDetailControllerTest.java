package com.jobnav.api.feature.user.web.controller;

import com.jobnav.api.feature.user.repository.UserRepository;
import com.jobnav.api.feature.user.web.dto.UserProfileDetailResponse;
import com.jobnav.api.feature.user.web.dto.UserProfileDetailUpdateRequest;
import com.jobnav.api.feature.user.web.facade.UserProfileFacade;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static java.util.Optional.ofNullable;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
@WebMvcTest(controllers = UserProfileDetailController.class,
        excludeFilters = {@ComponentScan.Filter(classes = Configuration.class), @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfig.class, ClientRegistrationRepository.class})},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class})
class UserProfileDetailControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TokenIntrospect tokenIntrospect;

    @MockBean
    AuthenticationDetailsService authenticationDetailsService;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    UserProfileFacade userProfileFacade;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Test
    @SneakyThrows
    void testGetUserProfile() {
        final UserProfileDetailResponse response = ofNullable(MockUtils.getResource("mock/job-applicant-profile-response.json", UserProfileDetailResponse.class))
                .orElse(new UserProfileDetailResponse());
        when(userProfileFacade.getUserProfileByUserId(any(String.class))).thenReturn(response);
        mockMvc.perform(get("/api/v1/profiles/{userId}", "1daf2218-842c-422e-a8d1-52b8ff318fa4"))
                .andExpect(jsonPath("$.userId", is("1daf2218-842c-422e-a8d1-52b8ff318fa4")))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testUpdateUserProfileDetail() {
        final String request = ofNullable(MockUtils.getResource("mock/job-applicant-profile-update-request.json", String.class)).orElse("");
        final UserProfileDetailResponse response = ofNullable(MockUtils.getResource("mock/job-applicant-profile-update-response.json", UserProfileDetailResponse.class))
                .orElse(new UserProfileDetailResponse());
        when(userProfileFacade.updateUserProfile(any(String.class), any(UserProfileDetailUpdateRequest.class))).thenReturn(response);
        mockMvc.perform(put("/api/v1/profiles/{userId}", "1daf2218-842c-422e-a8d1-52b8ff318fa4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(jsonPath("$.userId", is("1daf2218-842c-422e-a8d1-52b8ff318fa4")))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testUploadJobApplicantResume() {
        final MockMultipartFile file = new MockMultipartFile("file", "hello.pdf", MediaType.APPLICATION_PDF_VALUE, "Hello, World!".getBytes());
        doNothing().when(userProfileFacade).uploadJobApplicantResume(any(String.class), any(MultipartFile.class));
        mockMvc.perform(multipart("/api/v1/profiles/{jobApplicantId}/resume-upload", "1daf2218-842c-422e-a8d1-52b8ff318fa4")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print()).andExpect(status().isOk());
    }

}
