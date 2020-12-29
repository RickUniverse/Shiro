package com.shiro.realm;


import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lijichen
 * @date 2020/12/4 - 16:27
 */
public class ShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        System.out.println("[FirstRealm]");

        //1,把AuthenticationToken转换为UserNamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        //2,从UsernamePasswordToken中获取username
        String username = upToken.getUsername();
        //3,从数据库中查询username对应的username信息
        System.out.println("从数据库中获取username:"+username+ "锁对应的信息");
        //4，若用户不存在则抛出异常
        if ("unknown".equals(username)) {
            throw new UnknownAccountException("用户不存在！");
        }
        //5，是否抛出其他异常
        if ("monster".equals(username)) {
            throw new LockedAccountException("账户被锁定！");
        }
        System.out.println(upToken.getPassword());
        //可以是信息也可以是实体类对象
        Object principal = username;
        //密码
        Object credentials= "";//从数据库中获取的加密数据
        if ("admin".equals(username)) {
            credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
        }
        if ("user".equals(username)) {
            credentials = "098d2c478e9c11555ce2823231e02ec1";
        }
        //调用父类的getName方法即可
        String realmName = getName();
        //盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);//按照用户名当做盐值

        //6,根据用户情况来构建SimpleAuthenticationInfo
        SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal,credentials,realmName);
        info = new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);
        return info;
    }

    public static void main(String[] args) {
        int hashIterations = 1024;//加密次数
        String hashAlgorithmName = "MD5";//加密方式
        Object credentials = "123456";//加密数据
        Object salt = ByteSource.Util.bytes("admin");//盐值
        Object result = new SimpleHash(hashAlgorithmName,credentials,salt,hashIterations);
        System.out.println(result);
    }


    //进行授权操作，会被shiro调用的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1,从principalCollection中获取用户登录的信息
        Object principal = principalCollection.getPrimaryPrincipal();

        //2,利用登录的用户的信息来判断当前用户的权限和角色（可能需要查数据库）
        Set<String> roles = new HashSet<>();
        roles.add("user");//无论如何当前登录的一定是个用户角色
        if ("admin".equals(principal)) {
            roles.add("admin");//当前用户可以登录user也可以登录admin
        }

        //3,创建SimpleAuthorizationInfo并设置roles属性
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        //4,返回
        return info;
    }
}
