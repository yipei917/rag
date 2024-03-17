package com.edu.xmu.rag.core.exception;

import com.edu.xmu.rag.core.model.ReturnNo;

public class BusinessException extends RuntimeException{

    private final ReturnNo errno;

    public BusinessException(ReturnNo errno, String message) {
        super(message);
        this.errno = errno;
    }

    public BusinessException(ReturnNo errno) {
        super(errno.getMessage());
        this.errno = errno;
    }

    public ReturnNo getErrno(){
        return this.errno;
    }
}
