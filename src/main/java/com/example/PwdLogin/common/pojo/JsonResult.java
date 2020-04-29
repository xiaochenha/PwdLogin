package com.example.PwdLogin.common.pojo;

import com.example.PwdLogin.common.constant.SystemConstant;
import com.example.PwdLogin.common.enums.ErrorState;
import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable {
    private boolean success;
    private Object data;
    private String msg;
    private int code;
    public JsonResult(){
        super();
    }
    public JsonResult(boolean success, Object data, String msg, int code) {
        this.success = success;
        this.data = data;
        this.msg = msg;
        this.code = code;
    }
    public static JsonResult success(){
        return new JsonResult(true,null,null, SystemConstant.SUCCESS_STATUS_CODE);
    }
    public static JsonResult success(String msg){
        return new JsonResult(true,null,msg, SystemConstant.SUCCESS_STATUS_CODE);
    }
    public static JsonResult success(Object data){
        return new JsonResult(true,data,null, SystemConstant.SUCCESS_STATUS_CODE);
    }
    public static JsonResult fail(){
        return new JsonResult(false,null,null, SystemConstant.SUCCESS_STATUS_CODE);
    }
    public static JsonResult fail(String msg){
        return new JsonResult(false,null,msg, SystemConstant.SUCCESS_STATUS_CODE);
    }
    public static JsonResult error(ErrorState errorState){
        return new JsonResult(false,null,errorState.getMsg(),errorState.getCode());
    }
}
