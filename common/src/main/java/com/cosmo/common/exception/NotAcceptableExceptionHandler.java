package com.cosmo.common.exception;

import com.cosmo.common.constant.ServerResponseCodeConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotAcceptableExceptionHandler {
    @ExceptionHandler(value={NotAcceptableRequestException.class})
    public ResponseEntity<Object> handleNotAcceptableException(NotAcceptableRequestException e) {
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        String code = ServerResponseCodeConstant.VALIDATION_EXCEPTION;
        NotAcceptableException notAcceptableException = new NotAcceptableException(
                e.getMessage(),
                httpStatus,
                code
        );
        return new ResponseEntity<>(notAcceptableException, httpStatus);
    }
}