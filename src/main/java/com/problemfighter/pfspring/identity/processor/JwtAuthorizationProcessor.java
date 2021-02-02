package com.problemfighter.pfspring.identity.processor;

import com.problemfighter.pfspring.common.common.AppCommonUtil;
import com.problemfighter.pfspring.identity.config.IdentityMessages;
import com.problemfighter.pfspring.identity.model.entity.Identity;
import com.problemfighter.pfspring.identity.service.IdentityService;
import com.problemfighter.pfspring.jwt.config.JwtConfig;
import com.problemfighter.pfspring.jwt.model.data.JwtValidationResponse;
import com.problemfighter.pfspring.jwt.service.JwtService;
import com.problemfighter.pfspring.restapi.common.ApiRestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Component
public class JwtAuthorizationProcessor {

    private final JwtConfig jwtConfig;
    private final JwtService jwtService;
    private final IdentityService identityService;

    @Autowired
    public JwtAuthorizationProcessor(JwtConfig jwtConfig, JwtService jwtService, IdentityService identityService) {
        this.jwtConfig = jwtConfig;
        this.jwtService = jwtService;
        this.identityService = identityService;
    }

    public Boolean process(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        if (AppCommonUtil.stringIsNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            return false;
        }
        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "").trim();
        try {
            JwtValidationResponse jwtValidationResponse = jwtService.validateAccessToken(token);
            String issuerUUID = jwtValidationResponse.getIssuer();
            Identity identity = identityService.getActiveIdentityByUuid(issuerUUID);
            if (identity == null) {
                ApiRestException.unauthorized(IdentityMessages.INVALID_ACCESS_TOKEN);
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    identity.identifier,
                    identity.password,
                    null
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (Exception e) {
            ApiRestException.unauthorized(IdentityMessages.INVALID_ACCESS_TOKEN);
        }
        return false;
    }
}
