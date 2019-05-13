package com.fy.PermissionMannger.Config;


import com.fy.PermissionMannger.Security.MyFormAuthenticationFilter;
import com.fy.PermissionMannger.Security.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 〈Shiro配置类〉
 *
 * @author fangyan
 * @create 2019/5/7
 * @since 1.0.0
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/mainHome");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");

        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/assets/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/easyui/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");

        //自定义过滤器，解决登录成功后跳转至session保存的url中，
        //不知道为啥跳转到spring的小图标导致未成功跳转至首页
        Map map=new LinkedHashMap();
        map.put("authc",new MyFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(map);

        /*
        setUnauthorizedUrl失效原因：
        1、定义的filter必须满足AuthorizationFilter
        2、只有perms，roles，ssl，rest，port才是属于AuthorizationFilter
        3、而anon，authcBasic，auchc，user是AuthenticationFilter。
        4、所以项目中需要配置一个AuthorizationFilter。
        理解：同一个资源只能配置一个角色或多个角色，条件是与，也就是该
              用户必须具有多个角色。如果需要实现某资源多个角色共享就
              设置为perms[xxx]，权限。
        */
        //用户，需要角色权限
        filterChainDefinitionMap.put("/userInfo/deleteById/**", "roles[admin]");
        filterChainDefinitionMap.put("/userInfo/updateById", "roles[admin]");
        filterChainDefinitionMap.put("/userInfo/insert", "roles[admin]");
        filterChainDefinitionMap.put("/permission/**", "roles[admin]");
        filterChainDefinitionMap.put("/userInfo/findAllUsersPage", "perms[userInfo:view]");

        filterChainDefinitionMap.put("/logout", "logout");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     * @return
     */
    @Bean
    @Qualifier("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    @Bean
    @Qualifier("myShiroRealm")
    public MyShiroRealm myShiroRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myShiroRealm;
    }

    @Bean
    public SecurityManager securityManager(@Qualifier("myShiroRealm") MyShiroRealm myShiroRealm, @Qualifier("cacheManager")CacheManager cacheManager) {
        DefaultWebSecurityManager securityManager= new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myShiroRealm);
        return securityManager;
    }


    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /*@Bean(name="simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver
    createSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
        mappings.setProperty("UnauthorizedException","403");
        r.setExceptionMappings(mappings);  // None by default
        r.setDefaultErrorView("/error");    // No default
        r.setExceptionAttribute("ex");     // Default is "exception"
        //r.setWarnLogCategory("example.MvcLogger");     // No default
        return r;
    }*/
}
