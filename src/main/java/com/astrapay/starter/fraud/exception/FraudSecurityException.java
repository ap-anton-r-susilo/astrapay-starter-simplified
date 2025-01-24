package com.astrapay.starter.fraud.exception;

import lombok.Getter;

@Getter
public class FraudSecurityException extends RuntimeException{
    private String code;

    public FraudSecurityException(String code, String message) {
        super(message);
        this.code = code;
    }

    public FraudSecurityException(String s){
        super(s);
    }

}
