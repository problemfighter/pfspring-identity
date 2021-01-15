package com.problemfighter.pfspring.identity.common;

import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {

    public Object errorMessage;

    public AuthException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthException(String msg) {
        super(msg);
    }

    public AuthException(String message, Object errorMessage){
        super(message);
        this.errorMessage = errorMessage;
    }

    public Object getError(){
        return this.errorMessage;
    }

}
