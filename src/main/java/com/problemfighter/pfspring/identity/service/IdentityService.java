package com.problemfighter.pfspring.identity.service;


import com.problemfighter.pfspring.identity.model.dto.AuthResponse;
import com.problemfighter.pfspring.identity.model.dto.IAuthResponse;
import com.problemfighter.pfspring.identity.model.entity.Identity;
import com.problemfighter.pfspring.identity.repository.IdentityRepository;
import com.problemfighter.pfspring.jwt.service.JwtService;
import com.problemfighter.pfspring.restapi.rr.response.DetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentityService {

    private final IdentityRepository identityRepository;
    private final IdentityCallbackService identityCallbackService;
    private final JwtService jwtService;

    @Autowired
    public IdentityService(IdentityRepository identityRepository, IdentityCallbackService identityCallbackService, JwtService jwtService) {
        this.identityRepository = identityRepository;
        this.identityCallbackService = identityCallbackService;
        this.jwtService = jwtService;
    }

    public Identity getActiveIdentityByIdentifier(String identifier) {
        return identityRepository.getActiveIdentityByIdentifier(identifier);
    }


    public DetailsResponse<IAuthResponse> successAuthResponse(Identity identity) {
        IAuthResponse iAuthResponse = identityCallbackService.beforeAuthResponse(jwtService.getJwtProcessor(), identity);
        DetailsResponse<IAuthResponse> response = new DetailsResponse<>();
        if (iAuthResponse == null) {
            iAuthResponse = new AuthResponse().setToken(jwtService.getToken(identity.uuid), null);
        }
        response.data = iAuthResponse;
        response.success();
        return response;
    }

}
