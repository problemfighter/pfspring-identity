package com.problemfighter.pfspring.identity.service;

import com.problemfighter.pfspring.identity.model.dto.IAuthResponse;

public interface IIdentityCallback {

    public IAuthResponse beforeAuthResponse(IAuthResponse authResponse);

}
