package pers.ysc.boot.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author ysc
 * @Description shiro配置类
 * @Date 2020/11/11 21:32
 */
@Configuration
public class ShiroConfig {
    //Realm配置
    @Bean
    public MyRealm myRealm() {
        return new MyRealm();
    }

    //securityManager配置
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(myRealm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier(value="securityManager")DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //添加内置过滤器
         /* anon 无需认证即可访问
         authc 必须认证后才可以访问
         user 必须拥有 记住我才可以用(一般不常用)
         pers 必须拥有某个资源的权限才可以访问
         role 必须拥有某个角色才可以访问*/
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        filterChainDefinitionMap.put("/user/*","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //设置登录页面  用户登录map中的所有需要认证才可的页面都会自动跳转到此登录页面
        shiroFilterFactoryBean.setLoginUrl("/tologin");
        return shiroFilterFactoryBean;
    }
}
