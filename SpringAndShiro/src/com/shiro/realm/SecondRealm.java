package com.shiro.realm;


import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * @author lijichen
 * @date 2020/12/4 - 16:27
 */
public class SecondRealm extends AuthenticatingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        System.out.println("[SecondRealm]");

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
            credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
        }
        if ("user".equals(username)) {
            credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718";
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
        String hashAlgorithmName = "SHA1";//加密方式
        Object credentials = "123456";//加密数据
        Object salt = ByteSource.Util.bytes("user");//盐值
        Object result = new SimpleHash(hashAlgorithmName,credentials,salt,hashIterations);
        System.out.println(result);
    }
}
