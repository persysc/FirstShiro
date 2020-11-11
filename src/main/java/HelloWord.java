
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class HelloWord {
    private static final transient Logger log = LoggerFactory.getLogger(HelloWord.class);

    public static void main(String[] args) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        //单例的全局唯一
        SecurityUtils.setSecurityManager(securityManager);
        //获得当前用户
        Subject currentUser = SecurityUtils.getSubject();
        //得到当前对象的session对象
        Session session = currentUser.getSession();
        //session存值
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }
        //判断当前对象是否被认证
        if (!currentUser.isAuthenticated()) {
            //生成凭证
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            //设置rememberMe(记住我!)
            token.setRememberMe(true);
            try {
                //执行登录操作
                currentUser.login(token);
            } catch (UnknownAccountException uae) {//用户名不存在的情况
                log.info("There is no user with username of " + token.getPrincipal());//getPrincipal()得到凭证的信息
            } catch (IncorrectCredentialsException ice) {
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            } catch (AuthenticationException ae) {
            }
        }
        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");
        //判断当前对象是否有schwartz角色
        if (currentUser.hasRole("schwartz")) {
            log.info("May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }
        //判断是否有权限(粗粒度)
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }
        //注销
        currentUser.logout();
        System.exit(0);
    }
}