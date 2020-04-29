package com.example.PwdLogin.project.controller;

import com.example.PwdLogin.common.pojo.JsonResult;
import com.example.PwdLogin.project.service.IMemberUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IMemberUserService userService;
    @GetMapping("info")
    public JsonResult info(){
        return userService.userInfo();
    }
}
