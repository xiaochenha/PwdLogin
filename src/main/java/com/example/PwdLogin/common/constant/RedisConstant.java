package com.example.PwdLogin.common.constant;

public class RedisConstant {
    public static String emailCodeKey(String email){
        return "USER:REGISTE:CODE:"+email;
    }
}
