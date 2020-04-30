package com.example.PwdLogin.project.service.impl;

import com.example.PwdLogin.common.constant.RedisConstant;
import com.example.PwdLogin.common.pojo.JsonResult;
import com.example.PwdLogin.common.util.AppUtil;
import com.example.PwdLogin.framework.redis.RedisUtil;
import com.example.PwdLogin.project.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public JsonResult sendEmailCode(String email) {
        //生成验证码
        String emailCode = AppUtil.generaterCode();
        //使用springboot mail发送邮箱验证码
        boolean sendSuccess = sendEmail(email,"注册-验证码",emailCode);
        if(!sendSuccess){
            return JsonResult.fail("网络繁忙");
        }
        //发送成功后 将验证码放入缓存
        redisUtil.set(RedisConstant.emailCodeKey(email),emailCode,5*60);
        return JsonResult.success("已发送");
    }
    private boolean sendEmail(String toEmail, String subject, String content){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(content);
            javaMailSender.send(message);
        } catch (MailException exception) {
            return false;
        }
        return true;
    }
}
