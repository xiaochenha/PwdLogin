package com.example.PwdLogin.framework.shiro;

import com.example.PwdLogin.project.entity.MemberUser;
import com.example.PwdLogin.project.service.IMemberUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class EmailRealm extends AuthenticatingRealm {
    @Autowired
    private IMemberUserService userService;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String email = (String) authenticationToken.getPrincipal();
        MemberUser existUser = userService.getByEmail(email);
        if(null == existUser){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(email,existUser.getPassword(),this.getName());
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(existUser.getSalt()));
        return simpleAuthenticationInfo;
    }
}
