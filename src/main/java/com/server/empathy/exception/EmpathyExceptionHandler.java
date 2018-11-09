package com.server.empathy.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmpathyExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseException> baseExceptionHandler(BaseException e){
        return new ResponseEntity(e,e.getStatus());
    }

}
