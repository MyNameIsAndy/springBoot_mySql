package com.andy.service;

import com.andy.bean.User;

import java.util.List;

/**
 * Created by admin on 2019-01-11.
 */
public interface UserService {
    /**
     * 根据接口查询所用的用户
     */
     List<User> findAllUser();
}
