package com.lsy.lab.service;

import com.lsy.lab.exception.ExceptionCast;
import com.lsy.lab.mapper.RoleMapper;
import com.lsy.lab.mapper.UserMapper;
import com.lsy.lab.model.Role;
import com.lsy.lab.model.User;
import com.lsy.lab.model.vo.UserVo;
import com.lsy.lab.myUtil.MyNumberUtils;
import com.lsy.lab.myUtil.MyPassword;
import com.lsy.lab.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public Role getRoleByRole(Role role){

        return roleMapper.selectOne(role);
    }


}
