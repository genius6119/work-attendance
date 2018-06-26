package com.coder520.user.service;

import com.coder520.user.entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Create By Zhang on 2017/11/2
 */
public interface UserService {
    void createUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    User findUserByUserName(String username);
}
