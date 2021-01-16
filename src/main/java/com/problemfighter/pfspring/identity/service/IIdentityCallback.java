package com.problemfighter.pfspring.identity.service;

import com.problemfighter.pfspring.identity.model.dto.IAuthResponse;
import com.problemfighter.pfspring.identity.model.entity.Identity;
import com.problemfighter.pfspring.jwt.service.JwtService;

public interface IIdentityCallback {

    public IAuthResponse beforeAuthResponse(JwtService jwtProcessor, Identity identity);

}
