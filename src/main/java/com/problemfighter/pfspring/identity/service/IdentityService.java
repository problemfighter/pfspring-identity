package com.problemfighter.pfspring.identity.service;


import com.problemfighter.pfspring.identity.config.PasswordConfig;
import com.problemfighter.pfspring.identity.model.dto.identity.IdentityMasterDTO;
import com.problemfighter.pfspring.identity.model.entity.Identity;
import com.problemfighter.pfspring.identity.repository.IdentityRepository;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentityService implements RequestResponse {

    private final IdentityRepository identityRepository;

    @Autowired
    public IdentityService(IdentityRepository identityRepository) {
        this.identityRepository = identityRepository;
    }

    public Identity getActiveIdentityByIdentifier(String identifier) {
        return identityRepository.getActiveIdentityByIdentifier(identifier);
    }

    public Identity getActiveIdentityByUuid(String uuid) {
        return identityRepository.getActiveIdentityByUuid(uuid);
    }

    public Identity getIdentityByIdentifier(String identifier) {
        return identityRepository.getIdentityByIdentifier(identifier);
    }

    public Boolean isIdentityExist(String identifier) {
        if (getIdentityByIdentifier(identifier) == null) {
            return false;
        }
        return true;
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
