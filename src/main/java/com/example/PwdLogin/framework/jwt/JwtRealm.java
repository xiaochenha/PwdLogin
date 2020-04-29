package com.example.PwdLogin.framework.jwt;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

@Slf4j
public class JwtRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        String token = (String)authenticationToken.getCredentials();
        log.info("============== Token ==============="+token);
        boolean jwtEffective = JwtUtil.verify(token);
        if(!jwtEffective){
            throw new IncorrectCredentialsException("Token无效或已过期");
        }
        return new SimpleAuthenticationInfo(token,token,"JwtRealm");
    }
}
