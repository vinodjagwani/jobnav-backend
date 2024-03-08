package com.jobnav.api.feature.user.web.facade;

import com.jobnav.api.feature.resume.parser.impl.ResumeParserImpl;
import com.jobnav.api.feature.user.repository.entity.QUser;
import com.jobnav.api.feature.user.repository.entity.QUserProfile;
import com.jobnav.api.feature.user.repository.entity.User;
import com.jobnav.api.feature.user.repository.entity.UserProfile;
import com.jobnav.api.feature.user.service.UserProfileService;
import com.jobnav.api.feature.user.service.UserService;
import com.jobnav.api.feature.user.web.dto.JobApplicantProfileDetailResponse;
import com.jobnav.api.feature.user.web.dto.UserProfileDetailResponse;
import com.jobnav.api.feature.user.web.dto.UserProfileDetailUpdateRequest;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileFacade {

    UserService userService;

    ResumeParserImpl resumeParserImpl;

    UserProfileService userProfileService;

    public UserProfileDetailResponse getUserProfileByUserId(final String userId) {
        log.debug("Start querying user profile detail by userId [{}]", userId);
        final User user = userService.findOne(QUser.user.userId.eq(userId)).getOrElseThrow(ex -> ex);
        final UserProfileDetailResponse response = buildJobApplicantProfileDetailResponse(user);
        log.debug("Finish querying user profile detail with response [{}]", response);
        return response;
    }

    public UserProfileDetailResponse updateUserProfile(final String userId, final UserProfileDetailUpdateRequest request) {
        log.debug("Start calling save user profile detail by userId [{}] and userProfileDetailRequest [{}]", userId, request);
        final User user = userService.findOne(QUser.user.userId.eq(userId)).getOrElseThrow(ex -> ex);
        final UserProfile userProfile = userProfileService.findOne(QUserProfile.userProfile.user.userId.eq(user.getUserId())).getOrNull();
        ofNullable(userProfile).ifPresentOrElse(jap -> {
            final UserProfile updateUserProfile = buildUserProfile(request, jap);
            updateUserProfile.setUser(user);
            final UserProfile savedUserProfile = userProfileService.save(updateUserProfile);
            log.debug("Finish updating user profile detail with userProfileId [{}]", savedUserProfile.getUserProfileId());
        }, () -> {
            final UserProfile updateUserProfile = buildUserProfile(request, new UserProfile());
            updateUserProfile.setUser(user);
            final UserProfile savedUserProfile = userProfileService.save(updateUserProfile);
            log.debug("Finish saving user profile detail with userProfileId [{}]", savedUserProfile.getUserProfileId());
        });
        final UserProfileDetailResponse response = buildJobApplicantProfileDetailResponse(user);
        log.debug("Finish calling save user profile detail by response [{}]", response);
        return response;
    }

    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public void uploadJobApplicantResume(final String userId, final MultipartFile file) {
        log.debug("Start parsing resume with userId [{}]", userId);
        final UserProfile userProfile = userProfileService.findOne(QUserProfile.userProfile.user.userId.eq(userId)).getOrElseThrow(ex -> ex);
        final byte[] bytes = file.getBytes();
        final Path path = Files.write(Paths.get(getResumeUploadFolder() + file.getOriginalFilename()), bytes);
        final JSONObject jsonObject = resumeParserImpl.parseResume(path.toAbsolutePath().toString());
        userProfile.setResumeJson(jsonObject.toString());
        final UserProfile savedUserProfile = userProfileService.save(userProfile);
        log.debug("Finish parsing resume with jobApplicantId [{}] and jobApplicantProfileId [{}]", userId, savedUserProfile.getUserProfileId());
    }

    public Page<JobApplicantProfileDetailResponse> searchCandidateProfiles(final Predicate predicate, final Pageable pageable) {
        log.debug("Start calling searching candidate profile with predicate [{}] and pageable [{}]", predicate, pageable);
        final Page<UserProfile> userProfilePageable = userProfileService.findAll(predicate, pageable);
        final Page<JobApplicantProfileDetailResponse> response = userProfilePageable.map(this::buildJobApplicantProfileDetailResponse);
        log.debug("Finish calling searching candidate profile with no of records [{}]", response.getTotalElements());
        return response;
    }

    private UserProfileDetailResponse buildJobApplicantProfileDetailResponse(final User user) {
        final UserProfileDetailResponse response = new UserProfileDetailResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }

    private UserProfile buildUserProfile(final UserProfileDetailUpdateRequest request, final UserProfile userProfile) {
        userProfile.setFirstName(request.getFirstName());
        userProfile.setLastName(request.getLastName());
        userProfile.setMobile(request.getMobile());
        userProfile.setEmail(request.getEmail());
        userProfile.setPosition(request.getPositionTitle());
        userProfile.setAvailable(request.isAvailable());
        userProfile.setCurrentEmployed(request.isCurrentEmployed());
        userProfile.setLanguage(request.getLanguage());
        userProfile.setLocation(request.getLocation());
        userProfile.setVisaRequiredCurrent(request.isVisaRequiredCurrent());
        userProfile.setVisaRequiredFuture(request.isVisaRequiredFuture());
        userProfile.setSkills(request.getSkills());
        return userProfile;
    }

    private JobApplicantProfileDetailResponse buildJobApplicantProfileDetailResponse(final UserProfile userProfile) {
        final JobApplicantProfileDetailResponse response = new JobApplicantProfileDetailResponse();
        response.setJobApplicantProfileId(userProfile.getUserProfileId());
        response.setEmail(userProfile.getEmail());
        response.setLanguage(userProfile.getLanguage());
        response.setMobile(userProfile.getMobile());
        response.setFirstName(userProfile.getFirstName());
        response.setLastName(userProfile.getLastName());
        response.setSkills(userProfile.getSkills());
        response.setVisaRequiredCurrent(userProfile.isVisaRequiredCurrent());
        response.setPhoto(userProfile.getPhoto());
        response.setLocation(userProfile.getLocation());
        response.setPosition(userProfile.getPosition());
        response.setWorkExperience(userProfile.getWorkExperience());
        return response;
    }

    private String getResumeUploadFolder() {
        return System.getProperty("user.dir") + "/Resumes/";
    }
}
