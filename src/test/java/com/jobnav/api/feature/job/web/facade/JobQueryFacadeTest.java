package com.jobnav.api.feature.job.web.facade;


import com.google.common.collect.Lists;
import com.jobnav.api.feature.job.repository.entity.Job;
import com.jobnav.api.feature.job.repository.entity.QJob;
import com.jobnav.api.feature.job.service.JobService;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class JobQueryFacadeTest {

    @Mock
    JobService jobService;

    @InjectMocks
    JobQueryFacade jobQueryFacade;

    @Test
    void testQueryAllJobs() {
        when(jobService.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(new PageImpl<>(Lists.newArrayList(new Job())));
        jobQueryFacade.queryAllJobs(QJob.job.jobId.eq("12"), Pageable.ofSize(1));
        verify(jobService, atLeastOnce()).findAll(any(Predicate.class), any(Pageable.class));
    }
}
