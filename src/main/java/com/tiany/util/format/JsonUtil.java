package com.tiany.util.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtil {
    public static String obj2Json(Object obj) {
        String ret = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ret = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
        return ret;
    }
}
