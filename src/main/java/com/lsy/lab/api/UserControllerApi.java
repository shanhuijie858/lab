package com.lsy.lab.api;


import com.lsy.lab.model.User;
import com.lsy.lab.model.vo.UserVo;
import com.lsy.lab.request.UserListRequest;
import com.lsy.lab.response.QueryResponseResult;
import com.lsy.lab.response.QueryResult;
import com.lsy.lab.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Api(value = "用户信息管理", description = "对用户信息进行增删改查")
public interface UserControllerApi {
    @ApiOperation("根据userid,username,role_id，查询所有Vo用户信息")
    public QueryResponseResult findAllUserVo( Integer page,
                                              Integer size,
                                              UserListRequest userListRequest);

    @ApiOperation("根据userid查询Vo用户信息")
    public UserVo findUserVoByUserId(String user_id);
    @ApiOperation("更新用户基本信息")
    public ResponseResult update(UserVo userVo);

    @ApiOperation("更新用户密码")
    public ResponseResult updateUserPass(String id,String OldPass,String NewPass);

    @ApiOperation("通过id删除用户")
    public ResponseResult deleteUserById(String user_id);

    @ApiOperation("创建一条用户信息")
    public ResponseResult insertUser(User user);
}
