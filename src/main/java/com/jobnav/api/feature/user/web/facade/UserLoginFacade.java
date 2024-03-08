package com.jobnav.api.feature.user.web.facade;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.jobnav.api.feature.user.repository.entity.QUser;
import com.jobnav.api.feature.user.repository.entity.User;
import com.jobnav.api.feature.user.service.UserService;
import com.jobnav.api.feature.user.web.dto.UserLoginRequest;
import com.jobnav.api.feature.user.web.dto.UserLoginResponse;
import com.jobnav.api.security.util.JwtUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserLoginFacade {

    JwtUtils jwtUtils;

    UserService userService;

    AuthenticationManager authenticationManager;

    public UserLoginResponse loginUser(final UserLoginRequest request) {
        log.trace("Start login user with username [{}] or email [{}]", request.getUsername(), request.getEmail());
        final Authentication authentication = ofNullable(request.getEmail()).filter(StringUtils::isNotBlank).map(email -> {
            final User user = userService.findOne(QUser.user.email.eq(request.getEmail())).getOrElseThrow(ex -> ex);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword(), buildAuthorities(Lists.newArrayList(user.getUserType()))));
        }).orElseGet(() -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword(), buildAuthorities(Lists.newArrayList(request.getUserType())))));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = ((User) authentication.getPrincipal());
        final UserLoginResponse response = UserLoginResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .userType(user.getUserType())
                .accessToken(jwtUtils.generateJwtToken(authentication))
                .tokenType("Bearer").build();
        log.trace("End login user with userId [{}] and username [{}]", user.getUserId(), user.getUsername());
        return response;
    }

    @SneakyThrows
    public UserLoginResponse loginUser(final String accessToken) {
        log.trace("Start generate login user with accessToken [{}] ", accessToken);
        final DecodedJWT jwt = JWT.decode(accessToken);
        final String base64EncodedBody = jwt.getPayload();
        final String body = new String(Base64.getUrlDecoder().decode(base64EncodedBody));
        final Map<String, Object> map = new ObjectMapper().readValue(body, new TypeReference<>() {
        });
        final UserLoginResponse response = UserLoginResponse.builder()
                .userId(MapUtils.getString(map, "userId"))
                .username(MapUtils.getString(map, "username"))
                .email(MapUtils.getString(map, "email"))
                .userType(MapUtils.getString(map, "userType"))
                .accessToken(accessToken)
                .tokenType("Bearer").build();
        log.trace("Finish generate login user with accessToken [{}] ", accessToken);
        return response;
    }

    private Collection<? extends GrantedAuthority> buildAuthorities(final List<String> roles) {
        final List<GrantedAuthority> authorities = Lists.newArrayList();
        ListUtils.emptyIfNull(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        return authorities;
    }
}
