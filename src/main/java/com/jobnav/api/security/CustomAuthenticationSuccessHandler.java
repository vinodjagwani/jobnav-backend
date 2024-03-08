package com.jobnav.api.security;

import com.jobnav.api.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;

    @Value("${app.socialJwtTokenUrl}")
    String socialJwtTokenUrl;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        if (response.isCommitted()) {
            return;
        }
        final String token = jwtUtils.generateJwtToken(authentication);
        final String redirectionUrl = UriComponentsBuilder.fromUriString(socialJwtTokenUrl).queryParam("access_token", token)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
    }
}
