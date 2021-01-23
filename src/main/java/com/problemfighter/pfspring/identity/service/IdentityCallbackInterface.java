package com.problemfighter.pfspring.identity.service;

import com.problemfighter.pfspring.identity.model.dto.AuthResponseInterface;
import com.problemfighter.pfspring.identity.model.entity.Identity;
import com.problemfighter.pfspring.jwt.service.JwtService;

public interface IdentityCallbackInterface {
    AuthResponseInterface beforeAuthResponse(JwtService jwtService, Identity identity);
}
