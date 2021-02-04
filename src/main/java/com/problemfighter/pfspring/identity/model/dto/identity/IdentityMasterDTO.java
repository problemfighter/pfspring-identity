package com.problemfighter.pfspring.identity.model.dto.identity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.problemfighter.pfspring.jpacommon.model.dto.DTOCommon;
import com.problemfighter.pfspring.jpacommon.model.dto.DTOCommonGetter;
import com.problemfighter.pfspring.restapi.inter.model.RestDTO;

import javax.validation.constraints.NotNull;
import java.util.Date;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentityMasterDTO extends DTOCommon implements RestDTO {

    @NotNull(message = "Please enter identifier")
    public String identifier;

    @NotNull(message = "Please enter password")
    public String password;

}
