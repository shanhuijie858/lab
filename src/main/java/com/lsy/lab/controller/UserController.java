package com.lsy.lab.controller;

import com.lsy.lab.model.User;
import com.lsy.lab.result.Result;
import com.lsy.lab.result.ResultEnum;
import com.lsy.lab.result.ResultUtil;
import com.lsy.lab.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wzh
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public Result getAllStudents(){
        System.out.println("55555555");
        List<User> res = userService.getAllUser();
        System.out.println("66666");
        if(res!=null) {
            return ResultUtil.success(res);
        }else {
            return ResultUtil.error(ResultEnum.DATA_IS_NULL.getCode(),ResultEnum.DATA_IS_NULL.getMsg());
        }
    }

    @GetMapping("/test")
    public Result test(){
        return ResultUtil.success("hahaha");
    }





}