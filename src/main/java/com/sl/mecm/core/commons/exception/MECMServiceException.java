package com.sl.mecm.core.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.io.Serializable;

public class MECMServiceException extends RuntimeException{

    private final String code;
    private final Serializable errorDetail;
    private final HttpStatusCode outputStatusCode;

    public MECMServiceException(String code, String message, Serializable errorDetail) {
        super(message);
        this.code = code;
        this.errorDetail = errorDetail;
        this.outputStatusCode = HttpStatus.SERVICE_UNAVAILABLE;
    }

    public MECMServiceException(String code, String message, Serializable errorDetail, HttpStatusCode outputStatusCode) {
        super(message);
        this.code = code;
        this.errorDetail = errorDetail;
        this.outputStatusCode = outputStatusCode;
    }

    public MECMServiceException(String code, String message, Serializable errorDetail, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.errorDetail = errorDetail;
        this.outputStatusCode = HttpStatus.SERVICE_UNAVAILABLE;
    }

    public String getCode() {
        return code;
    }

    public Serializable getErrorDetail() {
        return errorDetail;
    }

    public HttpStatusCode getOutputStatusCode() {
        return outputStatusCode;
    }
}
