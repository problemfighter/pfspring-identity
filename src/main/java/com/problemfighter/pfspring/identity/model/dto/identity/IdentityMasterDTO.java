package com.problemfighter.pfspring.identity.model.dto.identity;

import com.problemfighter.pfspring.jpacommon.model.dto.DTOCommon;
import com.problemfighter.pfspring.restapi.inter.model.RestDTO;

import javax.validation.constraints.NotNull;


public class IdentityMasterDTO extends DTOCommon implements RestDTO {

    @NotNull(message = "Please enter identifier")
    public String identifier;

    @NotNull(message = "Please enter password")
    public String password;

}
