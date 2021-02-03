package com.problemfighter.pfspring.identity.controller;

import com.problemfighter.pfspring.identity.common.IdentityConstant;
import com.problemfighter.pfspring.identity.model.dto.AuthResponse;
import com.problemfighter.pfspring.identity.model.dto.AuthResponseInterface;
import com.problemfighter.pfspring.identity.model.dto.LoginDTO;
import com.problemfighter.pfspring.identity.model.dto.RenewDTO;
import com.problemfighter.pfspring.identity.service.IdentityService;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.DetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiV1AuthController implements RequestResponse {

    private final IdentityService identityService;

    @Autowired
    public ApiV1AuthController(IdentityService identityService) {
        this.identityService = identityService;
    }

    @RequestMapping(value = IdentityConstant.TOKEN_RENEW_URL, method = RequestMethod.POST)
    public DetailsResponse<AuthResponseInterface> renew(@RequestBody RequestData<RenewDTO> data) {
        return identityService.renew(data);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public DetailsResponse<AuthResponse> login(@RequestBody RequestData<LoginDTO> data) {
        AuthResponse authResponse = new AuthResponse();
        return responseProcessor().response(authResponse, AuthResponse.class);
    }

}
