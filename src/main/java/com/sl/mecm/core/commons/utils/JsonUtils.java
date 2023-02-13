package com.sl.mecm.core.commons.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

    private JsonUtils(){}

    public static boolean isInvalid(String text){
        if (!StringUtils.hasText(text)) return false;
        try {
            return JSON.parse(text) != null;
        }catch (Exception e){
            log.warn("invalid json!", e);
        }
        return false;
    }

    public static <T> T toObject(String text){
        return JSON.parseObject(text, new TypeReference<T>(){});
    }

    public static JSONObject toJsonObject(Object typeObject){
        if (typeObject == null) return null;
        return JSON.parseObject(JSON.toJSONString(typeObject));
    }

    public static JSONObject toJsonObject(String text){
        return JSON.parseObject(text);
    }
}
