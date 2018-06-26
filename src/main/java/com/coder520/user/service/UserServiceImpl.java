package com.coder520.user.service;

import com.coder520.common.utils.MD5Utils;
import com.coder520.user.dao.UserMapper;
import com.coder520.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Create By Zhang on 2017/11/2
 */
@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     *@Author Zhang
     *@Date 2017/11/4 14:14
     *@Description 根据用户名查询用户
     */

    @Override
    public User findUserByUserName(String username) {
        User user=userMapper.selectByUsername(username);
        return user;
    }
    @Override
    public void createUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        user.setPassword(MD5Utils.encrptyPassword(user.getPassword()));
        userMapper.insertSelective(user);
    }

}
