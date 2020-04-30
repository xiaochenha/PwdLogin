package com.example.PwdLogin;

import com.example.PwdLogin.common.pojo.JsonResult;
import com.example.PwdLogin.project.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendEmailTest {
    @Autowired
    private EmailService emailService;
    @Test
    public void testSendEmail(){
        JsonResult jsonResult = emailService.sendEmailCode("18636370334@163.com");
        System.out.println(jsonResult);
    }
}
