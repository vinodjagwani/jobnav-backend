package com.jobnav.api.feature.user.service.impl;

import com.jobnav.api.feature.user.repository.UserRepository;
import com.jobnav.api.feature.user.repository.entity.QUser;
import com.jobnav.api.feature.user.repository.entity.User;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static java.util.Optional.of;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceImplTest {

    @Mock
    PasswordEncoder encoder;

    @Mock
    UserRepository jobApplicationRepository;

    @InjectMocks
    UserServiceImpl jobApplicantService;

    @Test
    void testSave() {
        final User user = buildJobApplicant();
        when(encoder.encode(any(CharSequence.class))).thenReturn("test");
        when(jobApplicationRepository.save(any(User.class))).thenReturn(user);
        jobApplicantService.save(user);
        verify(jobApplicationRepository, atLeastOnce()).save(any(User.class));
    }

    @Test
    void testFindOne() {
        final User user = buildJobApplicant();
        when(jobApplicationRepository.findOne(any(Predicate.class))).thenReturn(of(user));
        jobApplicantService.findOne(QUser.user.userId.eq("13231"));
        verify(jobApplicationRepository, atLeastOnce()).findOne(any(Predicate.class));
    }

    private User buildJobApplicant() {
        final User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword("test");
        user.setUsername("324234");
        user.setEmail("test@gmail.com");
        user.setEnabled(true);
        return user;
    }
}
