package com.shiro.handler;

import com.shiro.service.ShiroAnnotationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author lijichen
 * @date 2020/12/4 - 19:17
 */
@Controller
@RequestMapping("/shiro")
public class RealmHandler {

    @Autowired
    private ShiroAnnotationService shiroAnnotationService;

    /**
     * 1，不是admin会抛出没有权限的异常，这时需要使用SpringMVC的声明式异常
     *,2，如果service层使用了事务，如果报异常，则需要将注解添加到当前Controller层，即testShiroAnnotation()方法上
     * @return
     */
    @RequestMapping("/testShiroAnnotation")
    public String testShiroAnnotation(HttpSession session){
        session.setAttribute("key","This is value！！");
        shiroAnnotationService.testShiroAnnotation();
        return "redirect:/views/list.jsp";
    }



    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {//测试当前用户是否已经被认证，即是否已经登录
            //将用户名和密码封装为一个UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //setRememberMe
            token.setRememberMe(true);
            try {
                System.out.println("tokenHashCode:"+token.hashCode());
                //执行登录
                currentUser.login(token);
            }catch (AuthenticationException ae) {}
        }
        return "redirect:/views/list.jsp";
    }
}
