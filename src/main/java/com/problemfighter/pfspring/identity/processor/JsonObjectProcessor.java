package com.problemfighter.pfspring.identity.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.problemfighter.pfspring.identity.config.IdentityMessages;
import com.problemfighter.pfspring.identity.model.dto.LoginDTO;
import com.problemfighter.pfspring.restapi.common.ApiRestException;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import javax.servlet.ServletInputStream;
import java.io.IOException;

public class JsonObjectProcessor {

    private ObjectMapper objectMapper;

    public JsonObjectProcessor() {
        this.objectMapper = new ObjectMapper();
    }

    public LoginDTO getLoginDetails(ServletInputStream inputStream) {
        try {
            RequestData<LoginDTO> requestData = objectMapper.readValue(inputStream, new TypeReference<RequestData<LoginDTO>>() {
            });
            return requestData.getData();
        } catch (IOException e) {
            ApiRestException.error(IdentityMessages.UNABLE_TO_PARSE);
        }
        return null;
    }

    public static JsonObjectProcessor instance() {
        return new JsonObjectProcessor();
    }

}
