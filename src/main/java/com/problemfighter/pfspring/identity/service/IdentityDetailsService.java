package com.problemfighter.pfspring.identity.service;

import com.problemfighter.pfspring.identity.config.IdentityMessages;
import com.problemfighter.pfspring.identity.model.auth.UserAuthDetails;
import com.problemfighter.pfspring.identity.model.entity.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class IdentityDetailsService implements UserDetailsService {

    private final IdentityService identityService;

    @Autowired
    public IdentityDetailsService(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Identity identity = identityService.getActiveIdentityByIdentifier(username);
        if (identity == null) {
            throw new UsernameNotFoundException(IdentityMessages.INVALID_IDENTIFIER_OR_PASS);
        }
        return new UserAuthDetails(identity);
    }

}
