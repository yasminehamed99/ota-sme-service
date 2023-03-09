package com.example.otasmeservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalControllerExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvoiceAlreadyExists.class)
    public ResponseEntity<Object>springHandleInvalidCertificateException(WebRequest response, InvoiceAlreadyExists e){
        return new ResponseEntity<Object>(new ApiError(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now()), HttpStatus.NOT_ACCEPTABLE);
    }
}
