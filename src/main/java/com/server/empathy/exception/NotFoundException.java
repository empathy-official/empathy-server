package com.server.empathy.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException{
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String ErrorMsg;

    public NotFoundException() { this.ErrorMsg = "Not Found"; }
    public NotFoundException(String errorMsg) {this.ErrorMsg = errorMsg; }
}
