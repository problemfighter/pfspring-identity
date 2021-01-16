package com.problemfighter.pfspring.identity.model.dto;

import javax.validation.constraints.NotEmpty;

public class RenewDTO {

    @NotEmpty(message = "Please enter token")
    public String token;
}
