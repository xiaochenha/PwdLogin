package com.example.PwdLogin.project.service;

import com.example.PwdLogin.common.pojo.JsonResult;

public interface EmailService {
    JsonResult sendEmailCode(String email);
}
