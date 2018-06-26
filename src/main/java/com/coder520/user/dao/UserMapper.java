package com.coder520.user.dao;

import com.coder520.user.entity.User;


public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * @Author Zhang
     * @Date 2017/11/4 14:16
     * @Description  根据用户名查询用户
     */
    User selectByUsername(String username);
}