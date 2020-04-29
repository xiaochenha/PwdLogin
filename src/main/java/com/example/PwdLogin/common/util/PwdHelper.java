package com.example.PwdLogin.common.util;

import lombok.Data;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.io.Serializable;
import java.util.UUID;

public final class PwdHelper {
    public static final int HASHLTERATIONS = 2;
    public static PwdInfo encryptPassword(String password){
        PwdInfo pwdInfo = new PwdInfo();
        pwdInfo.setSalt(UUID.randomUUID().toString().replace("-",""));
        Object encryptPwd = new Md5Hash(password,pwdInfo.getSalt(),HASHLTERATIONS);
        pwdInfo.setEncryptPwd(encryptPwd.toString());
        return pwdInfo;
    }
    @Data
    public static class PwdInfo implements Serializable {
        private static final long serialVersionUID = 1L;
        private String encryptPwd;
        private String salt;
    }
}
