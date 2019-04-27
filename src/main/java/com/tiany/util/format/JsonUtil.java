package com.tiany.util.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * json工具
 */
public abstract class JsonUtil {
    /**
     * 对象转json字符串
     *
     * @param obj
     * @return
     */
    public static String obj2Json(Object obj) {
        String ret = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ret = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return ret;
    }

    /**
     * 对象转json字符串
     *
     * @param obj
     * @return
     */
    public static String obj2FormatJson(Object obj) {
        String ret = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ret = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return ret;
    }

    /**
     * xml转换json
     *
     * @param xmlString
     * @return
     */
    public static String xml2Json(String xmlString) {
        String string = null;
        try {
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            string = jsonObject.toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
        return string;
    }
}
