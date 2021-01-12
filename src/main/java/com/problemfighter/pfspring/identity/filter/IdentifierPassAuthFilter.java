package com.problemfighter.pfspring.identity.filter;

import com.problemfighter.pfspring.identity.config.IdentityMessages;
import com.problemfighter.pfspring.identity.model.dto.LoginDTO;
import com.problemfighter.pfspring.identity.processor.JsonObjectProcessor;
import com.problemfighter.pfspring.restapi.common.ApiRestException;
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

public class IdentifierPassAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public IdentifierPassAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDTO loginData = JsonObjectProcessor.instance().getLoginDetails(request.getInputStream());
            if (loginData == null) {
                ApiRestException.error(IdentityMessages.UNABLE_TO_PARSE);
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginData.identifier,
                    loginData.password
            );
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            ApiRestException.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
