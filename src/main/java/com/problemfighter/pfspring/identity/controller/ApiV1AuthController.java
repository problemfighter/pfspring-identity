package com.problemfighter.pfspring.identity.controller;

import com.problemfighter.pfspring.identity.model.dto.IAuthResponse;
import com.problemfighter.pfspring.identity.model.dto.RenewDTO;
import com.problemfighter.pfspring.identity.service.IdentityService;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.DetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class ApiV1AuthController {

    private final IdentityService identityService;

    @Autowired
    public ApiV1AuthController(IdentityService identityService) {
        this.identityService = identityService;
    }

    @RequestMapping(value = "/renew", method = RequestMethod.POST)
    public DetailsResponse<IAuthResponse> renew(@RequestBody RequestData<RenewDTO> data) {
        return identityService.renew(data);
    }

}
