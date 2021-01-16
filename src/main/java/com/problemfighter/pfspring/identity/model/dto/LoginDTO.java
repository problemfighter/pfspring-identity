package com.problemfighter.pfspring.identity.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {

    @NotEmpty(message = "Please enter identifier")
    public String identifier;

    @NotEmpty(message = "Please enter password")
    public String password;

}
