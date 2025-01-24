package com.astrapay.starter.snapbi.exception;

public class CustomSnapBIException extends RuntimeException{

    private String code;

    public CustomSnapBIException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
