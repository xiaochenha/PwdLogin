package com.example.PwdLogin.framework.valid;

import org.apache.shiro.authc.AuthenticationException;

public class ValidException extends AuthenticationException {
    public ValidException(){
        super();
    }
    public ValidException(String message){
        super(message);
    }
    public ValidException(Throwable cause){
        super(cause);
    }
    public ValidException(String message, Throwable cause){
        super(message, cause);
    }
}
