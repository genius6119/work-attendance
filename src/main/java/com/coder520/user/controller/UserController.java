package com.coder520.user.controller;

import com.coder520.user.entity.User;
import com.coder520.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Create By Zhang on 2017/10/31
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
//    @RequestMapping("/index")
//    public String user(){
//        User user=new User();
//        user.setUsername("laozhou");
//        user.setPassword("laozhou123456");
//        user.setRealName("老周");
//        user.setMobile("12345678954");
//        return "user";
//    }


    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    /**
     * @Author Zhang
     * @Date 2017/11/9 19:02
     * @Description 获取用户信息
     */
    @RequestMapping("/userinfo")//路径
    @ResponseBody//表示这是一个异步请求
    public User getUser(HttpSession session){
        User user=(User)session.getAttribute("userinfo");
        return user;
    }

    /**
     *@Author Zhang
     *@Date 2017/11/9 19:14
     *@Description  登出方法
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
}
