package com.lsy.lab.controller;

import com.lsy.lab.api.UserControllerApi;
import com.lsy.lab.model.User;
import com.lsy.lab.model.vo.UserVo;
import com.lsy.lab.request.UserListRequest;
import com.lsy.lab.response.CommonCode;
import com.lsy.lab.response.QueryResponseResult;
import com.lsy.lab.response.ResponseResult;
import com.lsy.lab.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wzh
 */

@RestController
@RequestMapping("/lab/user")
@Slf4j
public class UserController implements UserControllerApi {

    @Autowired
    private UserService userService;

    @Override
    @GetMapping("/getAllUserVos/{page}/{size}")
    public QueryResponseResult findAllUserVo(@PathVariable("page")Integer page,
                                             @PathVariable("size")Integer size,
                                             UserListRequest userListRequest) {
        return userService.findAllUserVos(page,size,userListRequest);
    }

    @Override
    @GetMapping("/find/userId/{user_id}")
    public UserVo findUserVoByUserId(@PathVariable("user_id") String user_id){
        return userService.getUserVoByUserId(user_id);
    }
    @Override
    @PostMapping ("/insert")
    public ResponseResult insertUser(@RequestBody @Valid User user) {
        userService.insertUser(user);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @DeleteMapping(value ="/delete/{user_id}")
    public ResponseResult deleteUserById(@PathVariable("user_id") String user_id) {
        userService.deleteUserById(user_id);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    @Override
    @PutMapping("/update")
    public ResponseResult update(@RequestBody @Valid UserVo uservo){
        userService.updateUser(uservo);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /*更新密码*/
    @Override
    @PutMapping("/update/password")
    public ResponseResult updateUserPass(@RequestParam("id") String id,
                                         @RequestParam("OldPass") String OldPass,
                                         @RequestParam("NewPass") String NewPass){
        userService.updatePass(id,OldPass,NewPass);
        return new ResponseResult(CommonCode.SUCCESS);

    }


}