package com.lsy.lab.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsy.lab.exception.ExceptionCast;
import com.lsy.lab.mapper.SignMapper;
import com.lsy.lab.model.Sign;
import com.lsy.lab.model.vo.SignVo;
import com.lsy.lab.model.vo.UserVo;
import com.lsy.lab.myUtil.MyNumberUtils;
import com.lsy.lab.response.CommonCode;
import com.lsy.lab.response.QueryResponseResult;
import com.lsy.lab.response.QueryResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description: 签到服务
 * @Date
 */
@Service
public class SignService {
    @Autowired
    private SignMapper signMapper;

    @Autowired
    private UserService userService;

    /*
    * 条件：用户名称/用户工号   查询
    * */
    public QueryResponseResult findAllSigns(Integer page,
                                       Integer size,
                                       String userCount) {
        //分页
        Page pag =PageHelper.startPage(page,size);

        //...

        //解析分页结果
        PageInfo<SignVo> pageInfo = new PageInfo<SignVo>(pag.getResult());
        QueryResult queryResult = new QueryResult();
        //queryResult.setList(checkinVos);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    /*
    * 增
    * */
    @Transactional
    public void insertSign(Sign sign) {
        sign.setSignId(MyNumberUtils.getUUID());
        sign.setSignTime(new Date());
        int ins= signMapper.insert(sign);
        if(ins != 1){
            ExceptionCast.cast(CommonCode.INSERT_FAIL);
        }
    }

    /*
    * 根据user_id删除签到记录
    * */
    @Transactional
    public void deleteCheckinByUserId(String userId) {
        //1.先找
        Sign sign = new Sign();
        sign.setUserId(userId);
        List<Sign> select = signMapper.select(sign);
        if(CollectionUtils.isEmpty(select)){
            ExceptionCast.cast(CommonCode.DATA_IS_NULL);
        }
        //2.后删
        int i= 0;
        int sum = select.size();
        for(Sign sig : select){
            int del = signMapper.delete(sig);
            if(del == 1){
                i++;
            }
        }
        if(i != sum){
            ExceptionCast.cast(CommonCode.DELETE_FAIL);
        }
    }

    /*
    *根据签到id删除
    * */
    @Transactional
    public void deleteBySignId(String id){
        //1.先找
        Sign sign = new Sign();
        sign.setSignId(id);
        Sign select = signMapper.selectOne(sign);
        if(select == null){
            ExceptionCast.cast(CommonCode.DATA_IS_NULL);
        }
        //2.后删
        int del= signMapper.delete(select);
        if(del != 1){
            ExceptionCast.cast(CommonCode.DELETE_FAIL);
        }
    }


}
