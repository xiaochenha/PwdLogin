package com.example.PwdLogin.project.service;

import com.example.PwdLogin.common.pojo.JsonResult;
import com.example.PwdLogin.common.reqPojo.UserQuery;
import com.example.PwdLogin.project.entity.MemberUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChenPengfei
 * @since 2020-04-29
 */
public interface IMemberUserService extends IService<MemberUser> {
    MemberUser getByEmail(String email);

    JsonResult login(UserQuery userQuery);

    JsonResult regist(UserQuery userQuery);

    JsonResult userInfo();
}
