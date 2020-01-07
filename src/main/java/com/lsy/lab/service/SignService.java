package com.lsy.lab.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsy.lab.exception.ExceptionCast;
import com.lsy.lab.mapper.SignMapper;
import com.lsy.lab.model.Sign;
import com.lsy.lab.model.User;
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
import tk.mybatis.mapper.entity.Example;

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
                                       String userCount)  {
        List<Sign> signs = new ArrayList<Sign>();
        Page pag = new Page();
        if(StringUtils.isNotEmpty(userCount)){
            User user = userService.getUserByCount(userCount);
            pag =PageHelper.startPage(page,size);
            Example example = new Example(Sign.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userId",user.getUserId()); //参数为 属性名+值
            example.orderBy("signTime").desc();//排序
            signs = signMapper.selectByExample(example);
        }else{
            pag =PageHelper.startPage(page,size);
            Example example = new Example(Sign.class);
            Example.Criteria criteria = example.createCriteria();
            example.orderBy("signTime").desc();//排序
            signs = signMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(signs)) {
            ExceptionCast.cast(CommonCode.DATA_IS_NULL);
        }
        List<SignVo> signVos = new ArrayList<SignVo>();
        for(Sign sign : signs){
            SignVo sv = new SignVo();
            User user = userService.getUserById(sign.getUserId());
            sv.setSignId(sign.getSignId());
            sv.setSignTime(sign.getSignTime());
            sv.setUserId(sign.getUserId());
            sv.setUserCount(user.getUserCount());
            sv.setUserName(user.getUserName());
            signVos.add(sv);
        }
        //解析分页结果
        PageInfo<SignVo> pageInfo = new PageInfo<SignVo>(pag.getResult());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(signVos);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    /*
     *根据user-id 查询签到记录
     */
    public QueryResult getSignByUserId(String user_id, Integer page, Integer size) {
        if (StringUtils.isBlank(user_id)) {
            ExceptionCast.cast(CommonCode.SELECT_NULL);
            return null;
        }
        Page pag =PageHelper.startPage(page,size);
        Sign sign = new Sign();
        sign.setUserId(user_id);
        List<Sign> signs = signMapper.select(sign);
        if (CollectionUtils.isEmpty(signs)) {
            ExceptionCast.cast(CommonCode.DATA_IS_NULL);
        }
        //解析分页结果
        PageInfo<Sign> pageInfo = new PageInfo<>(pag.getResult());
        return new QueryResult(signs, pageInfo.getTotal());

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
    public void deleteSignByUserId(String userId) {
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
