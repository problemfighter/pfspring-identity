package com.problemfighter.pfspring.identity.model.auth;

import com.problemfighter.pfspring.identity.model.entity.Identity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserAuthDetails implements UserDetails {

    private Identity identity;

    public UserAuthDetails(Identity identity) {
        this.identity = identity;
    }

    public Identity getIdentity() {
        return identity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return identity.password;
    }

    @Override
    public String getUsername() {
        return identity.identifier;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
