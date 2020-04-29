package com.example.PwdLogin.common.enums;

public enum ErrorState {
    NEED_LOGIN(-1,"未登录"),
    NOT_PERMISSION(-2,"没有权限"),
    SYSTEM_BUSY(-3,"系统繁忙")
    ;
    private int code;
    private String msg;

    ErrorState(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
