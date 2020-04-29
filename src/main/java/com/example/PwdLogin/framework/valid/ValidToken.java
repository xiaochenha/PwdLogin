package com.example.PwdLogin.framework.valid;

import org.apache.shiro.authc.AuthenticationToken;

public interface ValidToken extends AuthenticationToken {
    void isValid() throws ValidException;
}
