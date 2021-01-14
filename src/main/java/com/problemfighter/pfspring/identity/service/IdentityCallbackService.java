package com.problemfighter.pfspring.identity.service;

import com.problemfighter.pfspring.identity.model.dto.IAuthResponse;
import com.problemfighter.pfspring.identity.model.entity.Identity;
import com.problemfighter.pfspring.jwt.processor.JwtProcessor;
import org.springframework.stereotype.Service;


@Service
public class IdentityCallbackService implements IIdentityCallback {


    @Override
    public IAuthResponse beforeAuthResponse(JwtProcessor jwtProcessor, Identity identity) {
        return null;
    }
}
