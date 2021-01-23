package com.problemfighter.pfspring.identity.model.dto;

public interface AuthResponseInterface {
    public TokenDTO getLogin();
    public void setLogin(TokenDTO tokenDTO);
}
