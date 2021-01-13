package com.problemfighter.pfspring.identity.service;


import com.problemfighter.pfspring.identity.model.entity.Identity;
import com.problemfighter.pfspring.identity.repository.IdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentityService {

    private final IdentityRepository identityRepository;

    @Autowired
    public IdentityService(IdentityRepository identityRepository) {
        this.identityRepository = identityRepository;
    }

    public Identity getActiveIdentityByIdentifier(String identifier){
        return identityRepository.getActiveIdentityByIdentifier(identifier);
    }
}
