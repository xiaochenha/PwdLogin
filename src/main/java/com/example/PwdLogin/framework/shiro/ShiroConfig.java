package com.example.PwdLogin.framework.shiro;

import com.example.PwdLogin.framework.jwt.JwtFilter;
import com.example.PwdLogin.framework.jwt.JwtRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public EmailRealm emailRealm(){
        EmailRealm emailRealm = new EmailRealm();
        emailRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return emailRealm;
    }
    @Bean
    public JwtRealm jwtRealm(){
        JwtRealm jwtRealm = new JwtRealm();
        return jwtRealm;
    }
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(customModularRealmAuthenticator());
        List<Realm> realmList = new ArrayList<>();
        realmList.add(emailRealm());
        realmList.add(jwtRealm());
        securityManager.setRealms(realmList);
        //关闭shiro自带session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/pub/need_login"); //未登录访问接口
        shiroFilterFactoryBean.setUnauthorizedUrl("/pub/not_permission"); //无权限接口
        shiroFilterFactoryBean.setSuccessUrl("/");
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        //filterChainDefinitionMap.put("/pub/regist","anon");
        //filterChainDefinitionMap.put("/pub/login","anon");
        filterChainDefinitionMap.put("/pub/**","anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("jwt",new JwtFilter());
        shiroFilterFactoryBean.setFilters(filters);
        filterChainDefinitionMap.put("/**","jwt");
        return shiroFilterFactoryBean;
    }
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }
    @Bean
    public CustomModularRealmAuthenticator customModularRealmAuthenticator(){
        CustomModularRealmAuthenticator customModularRealmAuthenticator = new CustomModularRealmAuthenticator();
        customModularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return customModularRealmAuthenticator;
    }
}
