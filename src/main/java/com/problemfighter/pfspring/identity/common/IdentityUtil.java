package com.problemfighter.pfspring.identity.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.problemfighter.pfspring.identity.config.IdentityMessages;
import com.problemfighter.pfspring.restapi.common.ApiRestException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public final class IdentityUtil {

    public static String objectToJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ApiRestException(IdentityMessages.JSON_CONVERT_ERROR);
        }
    }

    public static void makeResponse(HttpServletResponse response, String body, String responseType) {
        try {
            PrintWriter printWriter = response.getWriter();
            response.setContentType(responseType);
            response.setCharacterEncoding("UTF-8");
            printWriter.print(body);
            printWriter.flush();
        } catch (IOException e) {
            throw new ApiRestException(e.getMessage());
        }
    }

    public static void makeJsonResponse(HttpServletResponse response, Object object) {
        String json = objectToJsonString(object);
        makeResponse(response, json, "application/json");
    }

}
