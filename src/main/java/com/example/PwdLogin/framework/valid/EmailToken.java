package com.example.PwdLogin.framework.valid;

import lombok.Data;

@Data
public class EmailToken implements ValidToken {
    private String email;
    private String verificationCode;
    public EmailToken(){
        super();
    }

    public EmailToken(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
    }

    @Override
    public void isValid() throws ValidException {
        String redis_code = "666666"; //获取redis缓存中的邮箱验证码
        if(null == redis_code){ //判断 redis验证码不存在-验证码失效 抛出自定义异常
            throw new ValidException("验证码失效");
        }else if(!redis_code.equals(verificationCode)){ //redis验证码与用户输入验证码不相同-验证码错误 抛出自定义异常
            throw new ValidException("验证码错误");
        }
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
