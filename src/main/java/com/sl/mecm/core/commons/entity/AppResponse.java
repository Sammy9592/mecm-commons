package com.sl.mecm.core.commons.entity;

import com.alibaba.fastjson2.JSONObject;

import java.io.Serializable;

public class AppResponse implements Serializable{

    private String code;
    private String message;
    private Serializable data;

    public AppResponse(String code, String message, Serializable data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Serializable getData() {
        return data;
    }

    public JSONObject toJSONObject(){
        return JSONObject.of()
                .fluentPut("code", code)
                .fluentPut("message", message)
                .fluentPut("data", data);
    }
}
