package com.jobnav.api.feature.jobmailing.service.impl;

import com.jobnav.api.feature.jobmailing.repository.JobMailingRepository;
import com.jobnav.api.feature.jobmailing.repository.entity.JobMailing;
import com.jobnav.api.feature.jobmailing.service.JobMailingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JobMailingServiceImpl implements JobMailingService {

    JobMailingRepository jobMailingRepository;

    @Override
    public JobMailing save(final JobMailing jobMailing) {
        return jobMailingRepository.save(jobMailing);
    }
}
