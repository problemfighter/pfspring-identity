package com.problemfighter.pfspring.identity.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse implements AuthResponseInterface {

    public TokenDTO login;

    @Override
    public TokenDTO getLogin() {
        return this.login;
    }

    @Override
    public void setLogin(TokenDTO tokenDTO) {
        this.login = tokenDTO;
    }

    public AuthResponse setToken(String accessToken, String refreshToken) {
        login = new TokenDTO();
        login.accessToken = accessToken;
        login.refreshToken = refreshToken;
        return this;
    }
}
