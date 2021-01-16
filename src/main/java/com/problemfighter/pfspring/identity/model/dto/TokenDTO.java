package com.problemfighter.pfspring.identity.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDTO {

    public String accessToken;
    public String refreshToken;

}
