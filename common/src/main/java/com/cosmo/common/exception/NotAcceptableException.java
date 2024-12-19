package com.cosmo.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class NotAcceptableException {
    private final String message;
    private final HttpStatus httpStatus;
    private final String code;
}