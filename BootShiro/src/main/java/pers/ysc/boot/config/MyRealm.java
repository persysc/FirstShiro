package pers.ysc.boot.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ysc
 * @Description realm继承类
 * @Date 2020/11/11 21:33
 */
public class MyRealm extends AuthorizingRealm {
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权了");
        return null;
    }

    //认证(每次登录的时候会自动到此方法进行认证,尽管看起来表单甚至登录的controller都会这个方法没有一点联系)
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String name = "root";
        String password = "123";
        UsernamePasswordToken usertoken=(UsernamePasswordToken) authenticationToken;
        if (!usertoken.getUsername().equals(name)){
            return null;//会自动抛出异常用户名不存在的异常
        }
        //密码的认证是shiro自动认证的
        return new SimpleAuthenticationInfo("",password,"");
    }
}
