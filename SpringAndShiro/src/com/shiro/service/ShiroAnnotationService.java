package com.shiro.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author lijichen
 * @date 2020/12/5 - 16:05
 */
@Service
public class ShiroAnnotationService {

    @RequiresRoles({"admin"})//当前用户是否有admin权限
    public void testShiroAnnotation() {
        //使用Shiro的session可以很方便的访问到控制器层的session
        Session session = SecurityUtils.getSubject().getSession();
        Object key = session.getAttribute("key");
        System.out.println(key);
        System.out.println("time: "+new Date());
    }
}
