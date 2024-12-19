package com.cosmo.common.exception;

public class NotAcceptableRequestException extends RuntimeException{
    public NotAcceptableRequestException(String message) {
        super(message);
    }
}