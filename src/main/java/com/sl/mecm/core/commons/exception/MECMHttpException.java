package com.sl.mecm.core.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class MECMHttpException extends RuntimeException{

    private String code;
    private HttpStatusCode httpStatusCode;
    private String responseBody;

    public MECMHttpException(String code, HttpStatusCode httpStatusCode, String responseBody) {
        super(code + "; error on invoke http request");
        this.code = code;
        this.httpStatusCode = httpStatusCode;
        this.responseBody = responseBody;
    }

    public MECMHttpException(String code, HttpStatusCode httpStatusCode, String responseBody, Throwable cause) {
        super(code + "; error on invoke http request:" + cause.getLocalizedMessage(), cause);
        this.code = code;
        this.httpStatusCode = httpStatusCode;
        this.responseBody = responseBody;
    }

    public String getCode() {
        return code;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
