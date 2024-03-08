package com.jobnav.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.jobnav.api.exception.BusinessServiceException;
import com.jobnav.api.exception.dto.ErrorCodeEnum;
import com.jobnav.api.security.util.JwtUtils;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenIntrospect implements OpaqueTokenIntrospector {

    JwtUtils jwtUtils;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(final String token) {
        final Either<BusinessServiceException, OAuth2AuthenticatedPrincipal> oAuth2AuthenticatedPrincipals = Match(token)
                .of(Case($(v -> isFalse(jwtUtils.validateJwtToken(token))), Either.left(new BusinessServiceException(ErrorCodeEnum.UNAUTHORIZED, "authentication.error.invalid_token"))),
                        Case($(), Either.right(authenticate(decode(token)))));
        return oAuth2AuthenticatedPrincipals.getOrElseThrow(ex -> ex);
    }

    @SneakyThrows
    private Map<String, Object> decode(final String token) {
        final DecodedJWT jwt = JWT.decode(token);
        final String base64EncodedBody = jwt.getPayload();
        final String body = new String(Base64.getUrlDecoder().decode(base64EncodedBody));
        final Map<String, Object> map = new ObjectMapper().readValue(body, new TypeReference<>() {
        });
        map.put("token", token);
        map.put("iat", jwt.getIssuedAt().toInstant());
        map.put("exp", jwt.getExpiresAt().toInstant());
        return map;
    }

    private OAuth2AuthenticatedPrincipal authenticate(final Map<String, Object> map) {
        final String username = map.getOrDefault("username", "").toString();
        final List<GrantedAuthority> authorities = Lists.newArrayList(new SimpleGrantedAuthority("ROLE_" + map.getOrDefault("userType", "").toString()));
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, StringUtils.EMPTY, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return new DefaultOAuth2AuthenticatedPrincipal(username, map, authorities);
    }
}
