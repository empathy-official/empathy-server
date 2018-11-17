package com.server.empathy.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseException extends RuntimeException{

    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String ErrorMsg;


    public BaseException(){this.ErrorMsg = "Something is wrong";}
    public BaseException(String message){
        this.ErrorMsg = message;
    }


}
