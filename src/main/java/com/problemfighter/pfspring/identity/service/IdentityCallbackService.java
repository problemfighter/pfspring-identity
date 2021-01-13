package com.problemfighter.pfspring.identity.service;

import com.problemfighter.pfspring.identity.model.dto.IAuthResponse;
import org.springframework.stereotype.Service;


@Service
public class IdentityCallbackService implements IIdentityCallback {

    @Override
    public IAuthResponse beforeAuthResponse(IAuthResponse authResponse) {
        return authResponse;
    }
}
