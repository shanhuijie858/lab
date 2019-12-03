package com.lsy.lab.service;

import com.lsy.lab.mapper.UserMapper;
import com.lsy.lab.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public List<User> getAllUser() {
        System.out.println("2222");
        List<User> users = userMapper.selectAll();
        System.out.println(users);
        return users;
    }




}
