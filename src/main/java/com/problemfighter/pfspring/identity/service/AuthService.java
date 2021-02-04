package com.problemfighter.pfspring.identity.service;

import com.problemfighter.pfspring.identity.config.IdentityMessages;
import com.problemfighter.pfspring.identity.model.dto.AuthResponse;
import com.problemfighter.pfspring.identity.model.dto.AuthResponseInterface;
import com.problemfighter.pfspring.identity.model.dto.RenewDTO;
import com.problemfighter.pfspring.identity.model.entity.Identity;
import com.problemfighter.pfspring.identity.repository.IdentityRepository;
import com.problemfighter.pfspring.jwt.model.data.JwtValidationResponse;
import com.problemfighter.pfspring.jwt.processor.JWTException;
import com.problemfighter.pfspring.jwt.service.JwtService;
import com.problemfighter.pfspring.restapi.common.ApiRestException;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.ResponseProcessor;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.DetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements RequestResponse {


    private final JwtService jwtService;
    private final IdentityCallbackInterface identityCallbackInterface;
    private final IdentityRepository identityRepository;

    @Autowired
    public AuthService(JwtService jwtService, IdentityCallbackInterface identityCallbackInterface, IdentityRepository identityRepository) {
        this.jwtService = jwtService;
        this.identityCallbackInterface = identityCallbackInterface;
        this.identityRepository = identityRepository;
    }

    public DetailsResponse<AuthResponseInterface> successAuthResponse(Identity identity) {
        jwtService.setIssuer(identity.uuid);
        AuthResponseInterface iAuthResponse = identityCallbackInterface.beforeAuthResponse(jwtService, identity);
        DetailsResponse<AuthResponseInterface> response = new DetailsResponse<>();
        if (iAuthResponse == null) {
            iAuthResponse = new AuthResponse().setToken(jwtService.getAccessToken(), jwtService.getRefreshToken());
        }
        response.data = iAuthResponse;
        response.success();
        return response;
    }

    public Identity getActiveIdentityByIdentifier(String identifier) {
        return identityRepository.getActiveIdentityByIdentifier(identifier);
    }

    public DetailsResponse<AuthResponseInterface> renew(RequestData<RenewDTO> data) {
        RenewDTO renewDTO = data.getData();
        try {
            if (renewDTO != null && requestProcessor().dataValidate(renewDTO)) {
                JwtValidationResponse jwtValidationResponse = jwtService.validateRefreshToken(renewDTO.token);
                Identity identity = identityRepository.getActiveIdentityByUUID(jwtValidationResponse.getIssuer());
                if (identity == null) {
                    throw new ApiRestException().error(ResponseProcessor.unauthorized(IdentityMessages.UNABLE_TO_PROCESS_RENEW_REQUEST));
                }
                return successAuthResponse(identity);
            }
        } catch (JWTException jwtException) {
            throw new ApiRestException().error(ResponseProcessor.unauthorized(jwtException.getMessage()));
        } catch (Exception e) {
            throw new ApiRestException().errorException(e.getMessage());
        }
        throw new ApiRestException().errorException(IdentityMessages.UNABLE_TO_PROCESS_RENEW_REQUEST);
    }
}
