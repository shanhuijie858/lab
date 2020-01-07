package com.lsy.lab.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsy.lab.exception.ExceptionCast;
import com.lsy.lab.mapper.SeatMapper;
import com.lsy.lab.model.Seat;
import com.lsy.lab.model.Seat;
import com.lsy.lab.model.User;
import com.lsy.lab.model.vo.SeatVo;
import com.lsy.lab.myUtil.MyNumberUtils;
import com.lsy.lab.request.SeatListRequest;
import com.lsy.lab.response.CommonCode;
import com.lsy.lab.response.QueryResponseResult;
import com.lsy.lab.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shan HuiJie
 * @Description: 座位服务
 * @Date 2019.12.05 10:57
 */
@Service
public class SeatService {
    @Autowired
    private SeatMapper seatMapper;
    @Autowired
    private UserService userService;
    /*
    * 查询
    * 条件:座位编号/姓名
    * */
    public QueryResponseResult getAllSeat(Integer page,
                                          Integer size,
                                          SeatListRequest seatListRequest
                                          ){
        List<Seat> seats = new ArrayList<>();
        //如果用户没有输入条件则直接查询所有
        if(seatListRequest.getUserName() == null && seatListRequest.getSeatNum()== null && seatListRequest.getUserCount() == null){
            //seatListRequest =new SeatListRequest();
            seats = seatMapper.selectAll();
        }
        Seat seat = new Seat();
        Example example = new Example(Seat.class);
        Example.Criteria criteria = example.createCriteria();
        //编号不为空
        if(seatListRequest.getSeatNum() != null ){
            //seat.setSeatNum(seatListRequest.getSeatNum());
            criteria.andLike("seatNum", "%" + seatListRequest.getSeatNum() + "%");
        }
        //userCount不为空
        if(StringUtils.isNotEmpty(seatListRequest.getUserCount()) ){
            User userByCount = userService.getUserByCount(seatListRequest.getUserCount());
            //seat.setUserId(userByCount.getUserId());
            criteria.andEqualTo("userId", userByCount.getUserId());
        }
        //分页
        Page pag = PageHelper.startPage(page,size);
        //username 不为空
        if(StringUtils.isNotEmpty(seatListRequest.getUserName())){
            List<User> users = new ArrayList<>();
            //根据名字找userid集合
           users = userService.getUserByName(seatListRequest.getUserName());
            for(User user:users){
                Seat seat1= new Seat();
                //将座位编号赋值
                seat1.setSeatNum(seat.getSeatNum());
                //将user_id赋值
                seat1.setUserId(user.getUserId());
                //根据输入的座位编号以及用户姓名进行查询
                pag = PageHelper.startPage(page,size);
                seats.addAll(seatMapper.select(seat1));
            }
        }else{
            example.orderBy("seatNum").asc();//排序
            seats = seatMapper.selectByExample(example);
            //seats = seatMapper.select(seat);
        }
        if (CollectionUtils.isEmpty(seats)) {
            ExceptionCast.cast(CommonCode.DATA_IS_NULL);
        }
            List<SeatVo> seatVos = new ArrayList<>();
            for(Seat seat3:seats){
                SeatVo seatVo = new SeatVo();
                seatVo.setSeatId(seat3.getSeatId());
                seatVo.setUserName(userService.getUserById(seat3.getUserId()).getUserName());
                seatVo.setSeatCount(userService.getUserById(seat3.getUserId()).getUserCount());
                seatVo.setUserId(seat3.getUserId());
                seatVo.setSeatNum(seat3.getSeatNum());
                seatVos.add(seatVo);
            }
        //System.out.println(seatVos);
        //解析分页结果
        PageInfo<SeatVo> pageInfo = new PageInfo<SeatVo>(pag.getResult());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(seatVos);
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

    public void updateSeat(SeatVo seatVo){
        Seat seat = new Seat();
        User userByCount = userService.getUserByCount(seatVo.getSeatCount());
        //工号对应学生存在
        if( userByCount != null){
         seat.setUserId(userByCount.getUserId());
        }else{
            ExceptionCast.cast(CommonCode.USER_NOT_EXIST);
        }
        //坐位编号存在
        if(this.getSeatbyNum(seatVo.getSeatNum()) != null){
            seat.setSeatNum(seatVo.getSeatNum());
        }
        Example example =new Example(Seat.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("seatId",seatVo.getSeatId());
        int upd= seatMapper.updateByExampleSelective(seat,example);   //修改的部分值组成的对象
        if(upd != 1){
            ExceptionCast.cast(CommonCode.UPDATE_FAIL);
        }

    }

    @Transactional
    //@CacheEvict(value="MemberPoint",allEntries=true)
    public void deleteSeatById(String id){
        //id不为空
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(CommonCode.DATA_ERROR);
        }
        //1.先找
       if(this.getSeatById(id) != null){
           //2.后删
            int del = seatMapper.delete(this.getSeatById(id));
            if(del != 1){
                ExceptionCast.cast(CommonCode.DELETE_FAIL);
            }
        }else{
           ExceptionCast.cast(CommonCode.DATA_IS_NULL);
       }
    }

    @Transactional
    //@CacheEvict(value="MemberPoint",allEntries=true)
    public void deleteSeatByCount(String count){
        //id不为空
        if(StringUtils.isBlank(count)){
            ExceptionCast.cast(CommonCode.DATA_ERROR);
        }
        //1.先找 count-userId-seat
        List<Seat> seatByUserId = this.getSeatByUserId(userService.getUserByCount(count).getUserId());
        //2.后删
        int i= 0;
        int sum = seatByUserId.size();
        for (Seat po : seatByUserId) {
            int del = seatMapper.delete(po);
            if (del == 1) {
                i++;
            }
        }
        if(i != sum){
            ExceptionCast.cast(CommonCode.DELETE_FAIL);
        }
    }

}
