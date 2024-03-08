package com.jobnav.api.feature.user.web.facade;

import com.jobnav.api.feature.user.repository.entity.CompanyProfile;
import com.jobnav.api.feature.user.repository.entity.QCompanyProfile;
import com.jobnav.api.feature.user.service.CompanyProfileService;
import com.jobnav.api.feature.user.web.dto.CompanyProfileCreateRequest;
import com.jobnav.api.feature.user.web.dto.CompanyProfileCreateResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyProfileFacade {

    CompanyProfileService companyProfileService;

    public CompanyProfileCreateResponse createUpdateCompanyProfile(final String companyUserId, final CompanyProfileCreateRequest request) {
        log.debug("Start calling saving company profile data with companyUserId [{}]", companyUserId);
        final CompanyProfileCreateResponse response = new CompanyProfileCreateResponse();
        final CompanyProfile companyProfile = ofNullable(companyProfileService.findOne(QCompanyProfile.companyProfile.userId.eq(companyUserId)).getOrNull())
                .map(cp -> companyProfileService.save(buildCompanyProfile(companyUserId, request, cp)))
                .orElseGet(() -> companyProfileService.save(buildCompanyProfile(companyUserId, request, new CompanyProfile())));
        response.setCompanyProfileId(companyProfile.getCompanyProfileId());
        log.debug("Finish calling saving company profile data with companyProfileId [{}]", response.getCompanyProfileId());
        return response;
    }

    private CompanyProfile buildCompanyProfile(final String companyUserId, final CompanyProfileCreateRequest request, final CompanyProfile companyProfile) {
        companyProfile.setAboutCompany(request.getAboutCompany());
        companyProfile.setCompanyLogo(request.getCompanyLogo());
        companyProfile.setUserId(companyUserId);
        return companyProfile;
    }
}
