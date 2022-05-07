package com.benjamin.shirostudy.config;

import com.benjamin.shirostudy.realm.ShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zjw
 */
@Configuration
public class ShiroConfig {

//    @Bean
//    public SessionManager sessionManager(RedisSessionDAO sessionDAO) {
//        DefaultRedisWebSessionManager sessionManager = new DefaultRedisWebSessionManager();
//        sessionManager.setSessionDAO(sessionDAO);
//        return sessionManager;
//    }


//    @Bean
//    public DefaultWebSecurityManager securityManager(ShiroRealm realm, SessionManager sessionManager, RedisCacheManager redisCacheManager){
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(realm);
//        securityManager.setSessionManager(sessionManager);
//        // 设置CacheManager，提供与Redis交互的Cache对象
//        securityManager.setCacheManager(redisCacheManager);
//        return securityManager;
//    }

    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    @Bean
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/login.html","anon");
        filterChainDefinitionMap.put("/user/logout","logout");
        filterChainDefinitionMap.put("/user/**","anon");
//        filterChainDefinitionMap.put("/item/rememberMe","user");
//        filterChainDefinitionMap.put("/item/authentication","authc");
//        filterChainDefinitionMap.put("/item/select","rolesOr[超级管理员,运营]");
//        filterChainDefinitionMap.put("/item/delete","perms[item:delete,item:insert]");
        filterChainDefinitionMap.put("/**","authc");

        shiroFilterChainDefinition.addPathDefinitions(filterChainDefinitionMap);

        return shiroFilterChainDefinition;
    }

    @Value("#{ @environment['shiro.loginUrl'] ?: '/login.jsp' }")
    protected String loginUrl;

    @Value("#{ @environment['shiro.successUrl'] ?: '/' }")
    protected String successUrl;

    @Value("#{ @environment['shiro.unauthorizedUrl'] ?: null }")
    protected String unauthorizedUrl;


//    @Bean
//    protected ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,ShiroFilterChainDefinition shiroFilterChainDefinition) {
//
//        //1. 构建ShiroFilterFactoryBean工厂
//        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
//
//        //2. 设置了大量的路径
//        filterFactoryBean.setLoginUrl(loginUrl);
//        filterFactoryBean.setSuccessUrl(successUrl);
//        filterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
//
//        //3. 设置安全管理器
//        filterFactoryBean.setSecurityManager(securityManager);
//
//        //4. 设置过滤器链
//        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());
//
//        //5. 设置自定义过滤器 ， 这里一定要手动的new出来这个自定义过滤器，如果使用Spring管理自定义过滤器，会造成无法获取到Subject
//        filterFactoryBean.getFilters().put("rolesOr",new RolesOrAuthorizationFilter());
//
//
//
//        //6. 返回工厂
//        return filterFactoryBean;
//    }















}
