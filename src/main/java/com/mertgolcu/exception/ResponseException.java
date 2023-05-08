package com.mertgolcu.exception;

public class ResponseException extends Exception {

    private Integer code;
    private String customMessage;

    public ResponseException(String cm, Integer code) {
        this.customMessage = cm;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return createMessage();
    }

    private String createMessage() {
        return "Response Failed Cause -> code : " + code.toString() + " message : " + customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
