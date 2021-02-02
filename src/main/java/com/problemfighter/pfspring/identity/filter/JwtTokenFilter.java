package com.problemfighter.pfspring.identity.filter;


import com.problemfighter.pfspring.common.common.SpringContext;
import com.problemfighter.pfspring.identity.common.IdentityUtil;
import com.problemfighter.pfspring.identity.processor.JwtAuthorizationProcessor;
import com.problemfighter.pfspring.restapi.common.ApiRestException;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtTokenFilter extends OncePerRequestFilter implements JwtTokenFilterInterface, RequestResponse {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtAuthorizationProcessor jwtAuthorizationProcessor = SpringContext.getBean(JwtAuthorizationProcessor.class);
        try {
            jwtAuthorizationProcessor.process(request);
            filterChain.doFilter(request, response);
        } catch (Exception failed) {
            if (failed instanceof ApiRestException) {
                Object error = ((ApiRestException) failed).getError();
                if (error == null) {
                    error = responseProcessor().error(failed.getMessage());
                }
                IdentityUtil.makeJsonResponse(response, error);
            } else {
                IdentityUtil.makeJsonResponse(response, responseProcessor().error(failed.getMessage()));
            }
        }
    }

}
