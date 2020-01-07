package com.lsy.lab.api;

import com.lsy.lab.model.Seat;
import com.lsy.lab.model.vo.SeatVo;
import com.lsy.lab.request.SeatListRequest;
import com.lsy.lab.response.QueryResponseResult;

import com.lsy.lab.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Api(value = "座位信息管理", description = "对座位信息进行增删改查")
public interface SeatControllerApi {
    @ApiOperation("根据座位编号/姓名查询所有SeatVo用户信息")
    public QueryResponseResult getAllSeats(Integer page,Integer size,
                                           SeatListRequest seatListRequest);
    @ApiOperation("更新SeatVo信息")
    public ResponseResult update( SeatVo seatVo);

    @ApiOperation("新增Seat信息")
    public ResponseResult insertUser(Seat seat);
    @ApiOperation("根据seatid删除")
    public ResponseResult deleteSeatById( String id);
    @ApiOperation("根据userCount删除")
    public ResponseResult deleteSeatByUserCount( String count);
}
