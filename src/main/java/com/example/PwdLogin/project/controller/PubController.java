package com.example.PwdLogin.project.controller;

import com.example.PwdLogin.common.enums.ErrorState;
import com.example.PwdLogin.common.pojo.JsonResult;
import com.example.PwdLogin.common.reqPojo.UserQuery;
import com.example.PwdLogin.project.service.EmailService;
import com.example.PwdLogin.project.service.IMemberUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("pub")
public class PubController {
    @Autowired
    private IMemberUserService userService;
    @Autowired
    private EmailService emailService;
    @GetMapping("need_login")
    public JsonResult needLogin(){
        return JsonResult.error(ErrorState.NEED_LOGIN);
    }
    @GetMapping("not_permission")
    public JsonResult notPermission(){
        return JsonResult.error(ErrorState.NOT_PERMISSION);
    }
    @PostMapping("login")
    public JsonResult login(@RequestBody UserQuery userQuery){
        return userService.login(userQuery);
    }
    @PostMapping("regist")
    public JsonResult regist(@RequestBody UserQuery userQuery){
        return userService.regist(userQuery);
    }
    @GetMapping("send_email_code")
    public JsonResult sendEmailCode(@RequestParam("email") String email){
        return emailService.sendEmailCode(email);
    }
    @GetMapping("books")
    public JsonResult books(){
        List<String> books = new ArrayList<>();
        books.add("SpringBoot从入门到精通");
        books.add("Java设计模式");
        books.add("Shiro安全框架");
        books.add("Nginx");
        books.add("Vue项目实战");
        return JsonResult.success(books);
    }
}
