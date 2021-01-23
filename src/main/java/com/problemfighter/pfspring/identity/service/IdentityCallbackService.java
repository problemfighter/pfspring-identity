package com.problemfighter.pfspring.identity.service;

import com.problemfighter.pfspring.identity.model.dto.AuthResponseInterface;
import com.problemfighter.pfspring.identity.model.entity.Identity;
import com.problemfighter.pfspring.jwt.service.JwtService;
import org.springframework.stereotype.Service;

public class IdentityCallbackService implements IdentityCallbackInterface {


    @Override
    public AuthResponseInterface beforeAuthResponse(JwtService jwtService, Identity identity) {
        return null;
    }
}
