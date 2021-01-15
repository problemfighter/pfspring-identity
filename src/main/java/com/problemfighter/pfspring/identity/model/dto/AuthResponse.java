package com.problemfighter.pfspring.identity.model.dto;

public class AuthResponse implements IAuthResponse {

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