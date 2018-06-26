package com.coder520.common.security;

import com.coder520.common.utils.MD5Utils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * 自定义密码验证
 *
 *
 **/
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher{

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        try {
            UsernamePasswordToken usertoken = (UsernamePasswordToken) token;
            String password = String.valueOf(usertoken.getPassword());
            Object tokenCredentials = MD5Utils.encrptyPassword(password);
            Object accountCredentials =getCredentials(info);
            return equals(tokenCredentials,accountCredentials);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
