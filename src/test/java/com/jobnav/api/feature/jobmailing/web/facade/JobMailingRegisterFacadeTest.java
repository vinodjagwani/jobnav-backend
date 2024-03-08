package com.jobnav.api.feature.jobmailing.web.facade;


import com.jobnav.api.feature.jobmailing.repository.entity.JobMailing;
import com.jobnav.api.feature.jobmailing.service.JobMailingService;
import com.jobnav.api.feature.jobmailing.web.dto.JobMailingRegisterRequest;
import com.jobnav.api.feature.utils.MockUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Optional.ofNullable;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class JobMailingRegisterFacadeTest {

    @Mock
    JobMailingService jobMailingService;

    @InjectMocks
    JobMailingRegisterFacade jobMailingRegisterFacade;

    @Test
    void testRegisterJobMail() {
        final JobMailingRegisterRequest request = ofNullable(MockUtils.getResource("mock/job-applicant-mailing-create-request.json", JobMailingRegisterRequest.class))
                .orElse(new JobMailingRegisterRequest());
        when(jobMailingService.save(any(JobMailing.class))).thenReturn(new JobMailing());
        jobMailingRegisterFacade.registerJobMail(request);
        verify(jobMailingService, atLeastOnce()).save(any(JobMailing.class));
    }
}
