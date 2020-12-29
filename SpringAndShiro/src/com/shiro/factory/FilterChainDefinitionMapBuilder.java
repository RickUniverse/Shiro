package com.shiro.factory;

import java.util.LinkedHashMap;

/**
 * @author lijichen
 * @date 2020/12/5 - 16:20
 */
public class FilterChainDefinitionMapBuilder {
    public LinkedHashMap<String,String> FilterChainDefinitionMapBuilder() {
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("/views/login.jsp","anon");
        map.put("/shiro/login","anon");
        map.put("/shiro/logout","logout");
        map.put("/views/user.jsp","authc,roles[user]");//如果是remenber登陆的，则需要认证之后还需要有user的权限
        map.put("/views/admin.jsp","authc,roles[admin]");//如果是remenber登陆的，则需要认证之后还需要有admin的权限
        map.put("/views/list.jsp","user");//如果是通过remember登陆的也可以访问 list页面
        map.put("/**","authc");
        return map;
    }
}
