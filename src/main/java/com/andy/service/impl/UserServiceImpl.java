package com.andy.service.impl;

import com.andy.bean.User;
import com.andy.dao.mapper.UserMapper;
import com.andy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2019-01-11.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;


    public List<User> findAllUser() {
        List<User> list = userMapper.selectAll();
        return list;
    }
}
