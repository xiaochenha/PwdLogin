package com.example.PwdLogin.common.reqPojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserQuery implements Serializable {
    private String email; //邮箱
    private String password; //密码
    private String verificationCode; //验证码
}
