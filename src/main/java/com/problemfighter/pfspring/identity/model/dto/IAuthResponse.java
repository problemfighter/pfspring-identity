package com.problemfighter.pfspring.identity.model.dto;

public interface IAuthResponse {
    public TokenDTO getLogin();
    public void setLogin(TokenDTO tokenDTO);
}
