package com.lsy.lab.service;

import com.lsy.lab.exception.ExceptionCast;
import com.lsy.lab.mapper.UserMapper;
import com.lsy.lab.model.User;
import com.lsy.lab.model.vo.SeatVo;
import com.lsy.lab.myUtil.MyNumberUtils;
import com.lsy.lab.myUtil.MyPassword;
import com.lsy.lab.response.CommonCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /*
    * 根据学号/工号查找用户
    * */
   public User getUserByCount(String userCount){
       if(StringUtils.isBlank(userCount)){
           ExceptionCast.cast(CommonCode.DATA_ERROR);
       }
       User user = new User();
       user.setUserCount(userCount);
       User user1 = userMapper.selectOne(user);
       return  user1;
   }
    /*
     * 根据姓名查找用户
     * */
    public List<User> getUserByName(String userName){
        if(StringUtils.isBlank(userName)){
            ExceptionCast.cast(CommonCode.DATA_ERROR);
        }
        User user = new User();
        user.setUserName(userName);
        List<User> select = userMapper.select(user);
        return  select;
    }
    /*
     * 根据ID查找用户
     * */
    public User getUserById(String userId){
        User user = new User();
        user.setUserId(userId);
        User users = userMapper.selectOne(user);
        if(users == null){
            ExceptionCast.cast(CommonCode.USER_NOT_EXIST);
        }
        return users;
    }

    /*
    * 根据id删除用户
    * */
    @Transactional
    public void deleteUserById(String userId) {
        //id不为空
        if(StringUtils.isBlank(userId)){
            ExceptionCast.cast(CommonCode.DATA_ERROR);
        }
        //用户不为空
        if(this.getUserById(userId) != null){
            User user = new User();
            user.setUserId(userId);
            //清空座位表对应的信息??


            //用户信息删除
            int del = userMapper.delete(user);
            if( del != 1){
                ExceptionCast.cast(CommonCode.DELETE_FAIL);
            }

        }else{
            ExceptionCast.cast(CommonCode.DELETE_USER_NOT_EXIST);
        }
    }

    /**
     * 创建一条用户信息
     *
     * @param user
     * @return void
     */
    @Transactional
    public void insertUser(User user){
        if(user == null){
            //抛出异常，非法参数异常。指定异常信息的内容
            ExceptionCast.cast(CommonCode.DATA_IS_NULL);
        }
        user.setUserId(MyNumberUtils.getUUID());
        //密码加密加盐
        String pw = user.getUserPass();
        try {
            user.setUserPass(MyPassword.PasswrodEncoder(pw));
        } catch (Exception e) {
            ExceptionCast.cast(CommonCode.PASSWORD_ERROR);
        }
        //账号唯一性
        String count = user.getUserCount();
        if(this.getUserByCount(count) != null){
            ExceptionCast.cast(CommonCode.COUNT_EXIST);
        }else {
            user.setUserCount(count);
        }
        user.setUserState(true);//默认在
        user.setCreateTime(new Date());
    }


}
