package com.lsy.lab.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsy.lab.exception.ExceptionCast;
import com.lsy.lab.mapper.SeatMapper;
import com.lsy.lab.model.Seat;
import com.lsy.lab.model.vo.SeatVo;
import com.lsy.lab.myUtil.MyNumberUtils;
import com.lsy.lab.response.CommonCode;
import com.lsy.lab.response.QueryResponseResult;
import com.lsy.lab.response.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Shan HuiJie
 * @Description: 座位服务
 * @Date 2019.12.05 10:57
 */
public class SeatService {
    @Autowired
    private SeatMapper seatMapper;

    /*
    * 查询
    * 条件:座位编号/姓名
    * */
    public QueryResponseResult getAllSeat(Integer page,
                                          Integer size
                                          ){
        //分页
        Page pag = PageHelper.startPage(page,size);

        //...

        //解析分页结果
        PageInfo<SeatVo> pageInfo = new PageInfo<SeatVo>(pag.getResult());
        QueryResult queryResult = new QueryResult();
        //queryResult.setList(seatVos);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
    /*
    * 根据座位id查询
    * */
    public Seat getSeatById(String id){
        Seat seat = new Seat();
        seat.setSeatId(id);
        Seat sel = seatMapper.selectOne(seat);
        return sel;
    }
    /*
     * 根据Userid查询
     * */
    public List<Seat> getSeatByUserId(String id){
        Seat seat = new Seat();
        seat.setUserId(id);
        List<Seat> sel = seatMapper.select(seat);
        return sel;
    }
    /*
     * 根据座位编号查询
     * */
    public Seat getSeatbyNum(Integer num){
        Seat seat = new Seat();
        seat.setSeatNum(num);
        Seat sel = seatMapper.selectOne(seat);
        return sel;
    }
    /*
    * 新增座位
    * */
    public void insertSeat(Seat seat){
        seat.setSeatId(MyNumberUtils.getUUID());
        int ins = seatMapper.insert(seat);
        if(ins != 1){
            ExceptionCast.cast(CommonCode.INSERT_FAIL);
        }
    }

    /*
    * update座位信息
    * */

    public void updateSeat(Seat seat){

    }

}
