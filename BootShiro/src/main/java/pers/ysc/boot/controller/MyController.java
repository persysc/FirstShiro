package pers.ysc.boot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ysc
 * @Description controller层
 * @Date 2020/11/11 21:21
 */
@Controller
public class MyController {
    @GetMapping({"/", "/index"})
    public String toIndex(Model model) {
        model.addAttribute("mess", "HellWorld");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update() {
        return "user/update";
    }

    @RequestMapping("/tologin")
    public String login() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model) {
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token); //执行登录方法，如果没有异常就说明0K了
            return "index";
        } catch (UnknownAccountException e) { //用户名不存在
            model.addAttribute("msg", "用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e) { // 密码不存在
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }
}
