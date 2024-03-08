package com.jobnav.api.feature.jobmailing.service.impl;

import com.jobnav.api.feature.jobmailing.repository.JobMailingRepository;
import com.jobnav.api.feature.jobmailing.repository.entity.JobMailing;
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
class JobMailingServiceImplTest {

    @Mock
    JobMailingRepository jobMailingRepository;

    @InjectMocks
    JobMailingServiceImpl jobMailingServiceImpl;

    @Test
    void testSave() {
        final JobMailing jobMailing = buildJobMailing();
        when(jobMailingRepository.save(any(JobMailing.class))).thenReturn(jobMailing);
        jobMailingServiceImpl.save(jobMailing);
        verify(jobMailingRepository, atLeastOnce()).save(any(JobMailing.class));
    }

    private JobMailing buildJobMailing() {
        final JobMailing jobMailing = new JobMailing();
        jobMailing.setJobMailingId(UUID.randomUUID().toString());
        jobMailing.setEmail("test@gmail.com");
        return jobMailing;
    }
}
