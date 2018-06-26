package com.coder520.login.controller;

import com.coder520.common.utils.MD5Utils;
import com.coder520.user.entity.User;
import com.coder520.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Create By Zhang on 2017/11/3
 */
@Controller
@RequestMapping("login")
public class LoginController {
    @Autowired
    private UserService userService;
    /**
     *@Author Zhang
     *@Date 2017/11/4 14:12
     *@Description  登录页面
     */
    @RequestMapping
    public String login(){

        return "login";
    }

    /**
     * @Author Zhang
     * @Date 2017/11/4 14:13
     * @Description  校验登录
     */

    @RequestMapping("/check")
    @ResponseBody
    public String checkLogin(HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String username = request.getParameter("username");
        String pwd=request.getParameter("password");

//        UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);
////        token.setRememberMe(true);
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            subject.login(token);
//            SecurityUtils.getSubject().getSession().setTimeout(1800000);
//        } catch (Exception e) {
//            return "login_fail";
//        }
//        return "login_succ";

        /**
         *@Author Zhang
         *@Date 2017/11/7 18:40
         *@Description 查数据库 如果查到数据 调用MD5加密比对密码
         *
         * 先检查用户在不在，再检查密码对不对
         */
        User user=userService.findUserByUserName(username);
        if(user!=null){
            if(MD5Utils.checkPassword(pwd,user.getPassword())){
//       校验成功 用户信息存session 返回成功signal
                request.getSession().setAttribute("userinfo",user);
                return "login_succ";
            }else {
                return "login_fail";
            }
        }else {
//       如果校验失败，返回校验失败signal
            return "login_fail";
        }
    }

    /**
     * @Author Zhang
     * @Date 2017/11/3 23:32
     * @Description 添加用户
     */
    @RequestMapping("register")
    @ResponseBody
    public String register(@RequestBody User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userService.createUser(user);
        return "succ";
    }
}