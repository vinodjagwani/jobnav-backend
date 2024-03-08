package com.jobnav.api.feature.job.service.impl;

import com.jobnav.api.feature.job.repository.JobRepository;
import com.jobnav.api.feature.job.repository.entity.Job;
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
class JobServiceImplTest {

    @Mock
    JobRepository jobRepository;

    @InjectMocks
    JobServiceImpl jobService;

    @Test
    void testSave() {
        final Job job = buildJob();
        when(jobRepository.save(any(Job.class))).thenReturn(job);
        jobService.save(job);
        verify(jobRepository, atLeastOnce()).save(any(Job.class));
    }

    private Job buildJob() {
        final Job job = new Job();
        job.setJobId(UUID.randomUUID().toString());
        job.setJobType("test");
        job.setCareerPath("324234");
        job.setCompany("test");
        job.setDescription("test");
        job.setTitle("test");
        job.setLocation("test");
        job.setPosition("test");
        return job;
    }
}
