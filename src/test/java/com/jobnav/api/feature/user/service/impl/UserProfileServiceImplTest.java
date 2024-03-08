package com.jobnav.api.feature.user.service.impl;

import com.jobnav.api.feature.user.repository.UserProfileRepository;
import com.jobnav.api.feature.user.repository.entity.QUserProfile;
import com.jobnav.api.feature.user.repository.entity.UserProfile;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserProfileServiceImplTest {

    @Mock
    UserProfileRepository jobApplicationProfileRepository;

    @InjectMocks
    UserProfileServiceImpl jobApplicantProfileService;

    @Test
    void testFindOne() {
        final UserProfile userProfile = buildJobApplicantProfile();
        when(jobApplicationProfileRepository.findOne(any(Predicate.class))).thenReturn(Optional.of(userProfile));
        jobApplicantProfileService.findOne(QUserProfile.userProfile.userProfileId.eq("1"));
        verify(jobApplicationProfileRepository, atLeastOnce()).findOne(any(Predicate.class));
    }

    @Test
    void testSave() {
        final UserProfile userProfile = buildJobApplicantProfile();
        when(jobApplicationProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);
        jobApplicantProfileService.save(userProfile);
        verify(jobApplicationProfileRepository, atLeastOnce()).save(any(UserProfile.class));
    }

    private UserProfile buildJobApplicantProfile() {
        final UserProfile userProfile = new UserProfile();
        userProfile.setUserProfileId(UUID.randomUUID().toString());
        userProfile.setResumeJson("test");
        userProfile.setMobile("324234");
        userProfile.setLastName("test");
        userProfile.setFirstName("test");
        return userProfile;
    }
}
