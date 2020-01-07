package com.lsy.lab.api;

import com.lsy.lab.model.Sign;
import com.lsy.lab.response.QueryResponseResult;
import com.lsy.lab.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Shan HuiJie
 * @Description:
 * @Date 2019.12.17 22:44
 */
@Api(value = "签到信息管理", description = "对签到信息进行增、删、查")
public interface SignControllerApi {
    @ApiOperation("查询签到信息")
    public QueryResponseResult findAllSign(Integer page,
                                               Integer size,
                                              String userCount);
    @ApiOperation("根据user_id查询签到信息")
    public QueryResponseResult findSignByUserId( String user_id,
                                                    Integer page,
                                                   Integer size);
    @ApiOperation("插入一条签到信息")
    public ResponseResult insertSign(Sign Sign);

    @ApiOperation("根据user_id删除签到信息")
    public ResponseResult deleteSignByUserId(String userId);
    @ApiOperation("根据check_id删除签到信息")
    public ResponseResult deleteBySignId(String signId);
}
