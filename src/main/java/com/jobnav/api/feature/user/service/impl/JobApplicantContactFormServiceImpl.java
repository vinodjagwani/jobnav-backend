package com.jobnav.api.feature.user.service.impl;

import com.jobnav.api.feature.user.repository.JobApplicationContactFormRepository;
import com.jobnav.api.feature.user.repository.entity.JobApplicantContactForm;
import com.jobnav.api.feature.user.service.JobApplicantContactFormService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobApplicantContactFormServiceImpl implements JobApplicantContactFormService {

    JobApplicationContactFormRepository jobApplicationContactFormRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobApplicantContactForm save(final JobApplicantContactForm jobApplicantContactForm) {
        log.debug("Start saving job applicant contact form with jobApplicantContactFormId [{}]", jobApplicantContactForm.getJobApplicantContactFormId());
        final JobApplicantContactForm savedJobApplicantContactForm = jobApplicationContactFormRepository.save(jobApplicantContactForm);
        log.debug("Finish saving job applicant contact form with jobApplicantContactFormId [{}]", jobApplicantContactForm.getJobApplicantContactFormId());
        return savedJobApplicantContactForm;
    }
}
