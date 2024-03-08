package com.jobnav.api.feature.user.service.impl;

import com.jobnav.api.feature.user.repository.JobApplicationContactFormRepository;
import com.jobnav.api.feature.user.repository.entity.JobApplicantContactForm;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserContactFormServiceImplTest {

    @Mock
    JobApplicationContactFormRepository jobApplicationContactFormRepository;

    @InjectMocks
    JobApplicantContactFormServiceImpl jobApplicantContactFormService;

    @Test
    void testSave() {
        final JobApplicantContactForm jobApplicantContactForm = buildJobApplicantContactForm();
        when(jobApplicationContactFormRepository.save(any(JobApplicantContactForm.class))).thenReturn(jobApplicantContactForm);
        jobApplicantContactFormService.save(jobApplicantContactForm);
        verify(jobApplicationContactFormRepository, atLeastOnce()).save(any(JobApplicantContactForm.class));
    }

    private JobApplicantContactForm buildJobApplicantContactForm() {
        final JobApplicantContactForm jobApplicantContactForm = new JobApplicantContactForm();
        jobApplicantContactForm.setJobApplicantContactFormId(UUID.randomUUID().toString());
        jobApplicantContactForm.setMessage("test");
        jobApplicantContactForm.setPhone("324234");
        jobApplicantContactForm.setLastName("test");
        jobApplicantContactForm.setFirstName("test");
        return jobApplicantContactForm;
    }
}
