package com.server.empathy.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseException extends RuntimeException{

    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String ErrorMsg;


    public BaseException(){this.ErrorMsg = "Something is wrong";}
    public BaseException(String message){
        this.ErrorMsg = message;
    }


}
