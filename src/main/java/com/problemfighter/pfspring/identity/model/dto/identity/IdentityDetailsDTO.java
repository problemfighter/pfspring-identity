package com.problemfighter.pfspring.identity.model.dto.identity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentityDetailsDTO extends IdentityMasterDTO {

    public String name;
    public String email;
    public String mobile;

}
