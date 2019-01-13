package com.andy.controller;

import com.andy.bean.User;
import com.andy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by admin on 2019-01-11.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("list")
    public List<User> list(){
        List<User> list = userService.findAllUser();
        return list;
    }
}
