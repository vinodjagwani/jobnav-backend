package com.jobnav.api.feature.jobmailing.web.facade;


import com.jobnav.api.feature.jobmailing.repository.entity.JobMailing;
import com.jobnav.api.feature.jobmailing.service.JobMailingService;
import com.jobnav.api.feature.jobmailing.web.dto.JobMailingRegisterRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JobMailingRegisterFacade {

    JobMailingService jobMailingService;

    public void registerJobMail(final JobMailingRegisterRequest request) {
        log.debug("Start calling saving job mailing with request [{}]", request);
        final JobMailing jobMailing = jobMailingService.save(buildJobMailing(request));
        log.debug("Finish calling saving job mailing with jobMailingId [{}]", jobMailing.getJobMailingId());
    }

    private JobMailing buildJobMailing(final JobMailingRegisterRequest request) {
        final JobMailing jobMailing = new JobMailing();
        jobMailing.setEmail(request.getEmail());
        return jobMailing;
    }
}
