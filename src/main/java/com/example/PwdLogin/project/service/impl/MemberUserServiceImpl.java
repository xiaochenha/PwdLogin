package com.example.PwdLogin.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.PwdLogin.common.enums.ErrorState;
import com.example.PwdLogin.common.pojo.JsonResult;
import com.example.PwdLogin.common.reqPojo.UserQuery;
import com.example.PwdLogin.common.util.PwdHelper;
import com.example.PwdLogin.framework.jwt.JwtUtil;
import com.example.PwdLogin.framework.valid.EmailToken;
import com.example.PwdLogin.framework.valid.ValidException;
import com.example.PwdLogin.project.entity.MemberUser;
import com.example.PwdLogin.project.mapper.MemberUserMapper;
import com.example.PwdLogin.project.service.IMemberUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChenPengfei
 * @since 2020-04-29
 */
@Service
public class MemberUserServiceImpl extends ServiceImpl<MemberUserMapper, MemberUser> implements IMemberUserService {

    @Override
    public MemberUser getByEmail(String email) {
        QueryWrapper<MemberUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemberUser::getEmail,email);
        return getOne(queryWrapper);
    }

    @Override
    public JsonResult login(UserQuery userQuery) {
        String email = userQuery.getEmail();
        String password = userQuery.getPassword();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(email,password);
        try{
            subject.login(usernamePasswordToken);
            String token = JwtUtil.sign(email);
            Map<String,String> data = new HashMap<>(1);
            data.put("token",token);
            return JsonResult.success(data);
        }catch(UnknownAccountException e){
            return JsonResult.fail("账号不存在");
        }catch(IncorrectCredentialsException e){
            return JsonResult.fail("账号或密码错误");
        }
    }

    @Override
    public JsonResult regist(UserQuery userQuery) {
        try{
            String email = userQuery.getEmail();
            String verificationCode = userQuery.getVerificationCode();
            //邮箱验证码校验
            EmailToken emailToken = new EmailToken(email,verificationCode);
            emailToken.isValid();
            //判断用户是否存在 如果存在终止
            MemberUser existUser = getByEmail(email);
            if(null != existUser){
                return JsonResult.fail("邮箱已被注册");
            }
            //加密密码
            String password = userQuery.getPassword();
            PwdHelper.PwdInfo pwdInfo = PwdHelper.encryptPassword(password);
            //保存用户
            MemberUser newUser = new MemberUser();
            newUser.setEmail(email);
            newUser.setPassword(pwdInfo.getEncryptPwd());
            newUser.setSalt(pwdInfo.getSalt());
            boolean saveUserSuccess = save(newUser);
            if(saveUserSuccess){
                return JsonResult.success("注册成功");
            }
        }catch(ValidException e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.error(ErrorState.SYSTEM_BUSY);
    }

    @Override
    public JsonResult userInfo() {
        String email = JwtUtil.getEmail();
        MemberUser user = getByEmail(email);
        return JsonResult.success(user);
    }
}
