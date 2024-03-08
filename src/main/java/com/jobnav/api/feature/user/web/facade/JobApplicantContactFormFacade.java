package com.jobnav.api.feature.user.web.facade;

import com.jobnav.api.feature.user.repository.entity.JobApplicantContactForm;
import com.jobnav.api.feature.user.service.JobApplicantContactFormService;
import com.jobnav.api.feature.user.web.dto.JobApplicantContactFormCreateRequest;
import com.jobnav.api.feature.user.web.dto.JobApplicantContactFormCreateResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobApplicantContactFormFacade {

    JobApplicantContactFormService jobApplicantContactFormService;

    public JobApplicantContactFormCreateResponse createJobApplicantContactForm(final JobApplicantContactFormCreateRequest request) {
        log.debug("Start calling saving job applicant contact form request [{}]", request);
        final JobApplicantContactForm jobApplicantContactForm = jobApplicantContactFormService.save(buildJobApplicantContactForm(request));
        final JobApplicantContactFormCreateResponse response = buildJobApplicantContactFormCreateResponse(jobApplicantContactForm);
        log.debug("End calling saving job applicant contact form response [{}]", response);
        return response;
    }

    private JobApplicantContactForm buildJobApplicantContactForm(final JobApplicantContactFormCreateRequest request) {
        final JobApplicantContactForm jobApplicantContactForm = new JobApplicantContactForm();
        jobApplicantContactForm.setFirstName(request.getFirstName());
        jobApplicantContactForm.setLastName(request.getLastName());
        jobApplicantContactForm.setEmail(request.getEmail());
        jobApplicantContactForm.setPhone(request.getPhone());
        jobApplicantContactForm.setMessage(request.getMessage());
        return jobApplicantContactForm;
    }

    private JobApplicantContactFormCreateResponse buildJobApplicantContactFormCreateResponse(final JobApplicantContactForm jobApplicantContactForm) {
        final JobApplicantContactFormCreateResponse response = new JobApplicantContactFormCreateResponse();
        response.setJobApplicantContactFormId(jobApplicantContactForm.getJobApplicantContactFormId());
        return response;
    }
}
