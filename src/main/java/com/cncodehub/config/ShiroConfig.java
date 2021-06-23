package com.cncodehub.config;

import com.cncodehub.shiro.AccountRealm;
import com.cncodehub.shiro.JwtFilter;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;

/**
 * shiro启用注解拦截控制器
 */
@Configuration
public class ShiroConfig {

    @Autowired
    JwtFilter jwtFilter;

    //这里 redisSessionDAO redisCacheManager 报错是因为编译器，不是因为真的错了

    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // inject redisSessionDAO
        sessionManager.setSessionDAO(redisSessionDAO);
        // other stuff...
        return sessionManager;
    }

    @Bean
    public SessionsSecurityManager securityManager(AccountRealm realms, SessionManager sessionManager, RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realms);
        //inject sessionManager
        securityManager.setSessionManager(sessionManager);
        // inject redisCacheManager
        securityManager.setCacheManager(redisCacheManager);
        // other stuff...
        return securityManager;
    }

    /**
     * 配置了基本的过滤规则
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        Map<String, String> filterMap = new LinkedHashMap<>();

        //下面两句就是表示是可以匿名访问还是可以咋样咋样
        filterMap.put("/**", "jwt");
        //filterMap.put("/**", "authc");
        chainDefinition.addPathDefinitions(filterMap);
        return chainDefinition;
    }


    /**
     *Shiro使用认证和授权时，都是通过ShiroFilterFactoryBean设置一些Shiro的拦截器进行的,
     * 拦截器会以LinkedHashMap的形式存储需要拦截的资源和链接
     * 值为拦截的形式
     * 比如authc: 所有的URL都必须认证通过才可以访问
     * anon: 所有URL都可以匿名访问
     * 拦截的时候可以使用通配符 比如/**为拦截所有
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", jwtFilter);
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();

        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }


}
