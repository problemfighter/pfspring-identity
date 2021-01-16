package com.problemfighter.pfspring.identity.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {

    public String identifier;
    public String password;
}
