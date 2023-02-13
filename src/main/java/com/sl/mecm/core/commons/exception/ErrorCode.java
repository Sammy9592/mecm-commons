package com.sl.mecm.core.commons.exception;

public enum ErrorCode {

    SUCCESS("200"), ILLEGAL_PARAM("400"), NO_AUTH("403"), ERROR("503");

    private final String code;

    ErrorCode(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
