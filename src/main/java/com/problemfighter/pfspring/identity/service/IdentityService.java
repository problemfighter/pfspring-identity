package com.problemfighter.pfspring.identity.service;


import com.problemfighter.pfspring.identity.config.IdentityMessages;
import com.problemfighter.pfspring.identity.config.PasswordConfig;
import com.problemfighter.pfspring.identity.model.dto.AuthResponse;
import com.problemfighter.pfspring.identity.model.dto.IAuthResponse;
import com.problemfighter.pfspring.identity.model.dto.RenewDTO;
import com.problemfighter.pfspring.identity.model.dto.identity.IdentityMasterDTO;
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
public class IdentityService implements RequestResponse {

    private final IdentityRepository identityRepository;
    private final IIdentityCallbackService iIdentityCallbackService;
    private final JwtService jwtService;

    @Autowired
    public IdentityService(IdentityRepository identityRepository, IIdentityCallbackService iIdentityCallbackService, JwtService jwtService) {
        this.identityRepository = identityRepository;
        this.iIdentityCallbackService = iIdentityCallbackService;
        this.jwtService = jwtService;
    }

    public Identity getActiveIdentityByIdentifier(String identifier) {
        return identityRepository.getActiveIdentityByIdentifier(identifier);
    }

    public DetailsResponse<IAuthResponse> renew(RequestData<RenewDTO> data) {
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


    public DetailsResponse<IAuthResponse> successAuthResponse(Identity identity) {
        jwtService.setIssuer(identity.uuid);
        IAuthResponse iAuthResponse = iIdentityCallbackService.beforeAuthResponse(jwtService, identity);
        DetailsResponse<IAuthResponse> response = new DetailsResponse<>();
        if (iAuthResponse == null) {
            iAuthResponse = new AuthResponse().setToken(jwtService.getAccessToken(), jwtService.getRefreshToken());
        }
        response.data = iAuthResponse;
        response.success();
        return response;
    }

    public Identity createIdentity(IdentityMasterDTO data) {
        Identity identity = requestProcessor().process(data, Identity.class);
        if (identity.password != null) {
            identity.password = new PasswordConfig().passwordEncoder().encode(identity.password);
        }
        identityRepository.save(identity);
        return identity;
    }

    public Long getIdentityCount() {
        return identityRepository.count();
    }

}
