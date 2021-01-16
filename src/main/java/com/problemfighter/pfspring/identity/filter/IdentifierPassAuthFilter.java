package com.problemfighter.pfspring.identity.filter;

import com.problemfighter.pfspring.identity.common.AuthException;
import com.problemfighter.pfspring.identity.common.IdentityUtil;
import com.problemfighter.pfspring.identity.config.IdentityMessages;
import com.problemfighter.pfspring.identity.model.dto.LoginDTO;
import com.problemfighter.pfspring.identity.model.entity.Identity;
import com.problemfighter.pfspring.identity.processor.JsonObjectProcessor;
import com.problemfighter.pfspring.identity.service.IdentityService;
import com.problemfighter.pfspring.restapi.common.ApiRestException;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IdentifierPassAuthFilter extends UsernamePasswordAuthenticationFilter implements RequestResponse {

    private final AuthenticationManager authenticationManager;
    private final IdentityService identityService;
    private Identity identity;

    public IdentifierPassAuthFilter(AuthenticationManager authenticationManager, IdentityService identityService) {
        this.authenticationManager = authenticationManager;
        this.identityService = identityService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDTO loginData = JsonObjectProcessor.instance().getLoginDetails(request.getInputStream());
            if (loginData == null) {
                throw new ApiRestException().errorException(IdentityMessages.UNABLE_TO_PARSE);
            }

            identity = identityService.getActiveIdentityByIdentifier(loginData.identifier);
            if (identity == null) {
                throw new ApiRestException().errorException(IdentityMessages.INVALID_IDENTIFIER_OR_PASS);
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginData.identifier,
                    loginData.password
            );
            return authenticationManager.authenticate(authentication);
        } catch (Exception e) {
            if (e instanceof ApiRestException) {
                Object error = ((ApiRestException) e).getError();
                throw new AuthException(e.getMessage(), error);
            } else {
                throw new AuthException(e.getMessage());
            }
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        IdentityUtil.makeJsonResponse(response, identityService.successAuthResponse(identity));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (failed instanceof AuthException) {
            Object error = ((AuthException) failed).getError();
            if (error == null) {
                error = responseProcessor().error(failed.getMessage());
            }
            IdentityUtil.makeJsonResponse(response, error);
        } else {
            IdentityUtil.makeJsonResponse(response, responseProcessor().error(failed.getMessage()));
        }
    }
}
