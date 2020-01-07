package com.lsy.lab.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsy.lab.exception.ExceptionCast;
import com.lsy.lab.mapper.RoleMapper;
import com.lsy.lab.mapper.SeatMapper;
import com.lsy.lab.mapper.UserMapper;
import com.lsy.lab.model.Role;
import com.lsy.lab.model.Seat;
import com.lsy.lab.model.User;
import com.lsy.lab.model.vo.SeatVo;
import com.lsy.lab.model.vo.SignVo;
import com.lsy.lab.model.vo.UserVo;
import com.lsy.lab.myUtil.GetAgeByBirthUtils;
import com.lsy.lab.myUtil.MyNumberUtils;
import com.lsy.lab.myUtil.MyPassword;
import com.lsy.lab.request.UserListRequest;
import com.lsy.lab.response.CommonCode;
import com.lsy.lab.response.QueryResponseResult;
import com.lsy.lab.response.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.*;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private SeatService seatService;
    @Autowired
    private SignService signService;
    @Autowired
    private SeatMapper seatMapper;

    /*根据user_id,学号,姓名查找学生*/
    public QueryResponseResult findAllUserVos(Integer page,
                                              Integer size,
                                              UserListRequest userListRequest) {
        Date date = new Date();
        //分页
        Page pag = PageHelper.startPage(page,size);
        //判断请求条件的合法性
        if (userListRequest == null){
            userListRequest = new UserListRequest();
        }
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //判断不为空字符串
        if(StringUtils.isNotEmpty(userListRequest.getUserCount())){
            //过滤条件
            criteria.andLike("userCount", "%" + userListRequest.getUserCount() + "%");
        }
        if(StringUtils.isNotEmpty(userListRequest.getUserName())){
            criteria.andLike("userName", "%" + userListRequest.getUserName() + "%");
        }
        if(StringUtils.isNotEmpty(userListRequest.getRoleId())){
            criteria.andEqualTo("roleId",  userListRequest.getRoleId());
        }
        //查询
        example.orderBy("createTime").desc();//排序
        List<User> users = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(users)) {
            ExceptionCast.cast(CommonCode.DATA_IS_NULL);
        }
        List<UserVo> userVos = new ArrayList<UserVo>();
        for(User user:users){
            //座位编号
            UserVo uservo = new UserVo();
           // List<Seat> seatByUserId = seatService.getSeatByUserId(user.getUserId());
           // for(Seat seat1 : seatByUserId){
               // uservo.setSeatNum(seat1.getSeatNum());
                String roleId=user.getRoleId();
                Role role = new Role();
                role.setRoleId(roleId);
                uservo.setRoleName(roleMapper.selectOne(role).getRoleName());
                uservo.setRoleId(user.getRoleId());
                uservo.setUserId(user.getUserId());
                uservo.setUserGrade(user.getUserGrade());
                uservo.setUserName(user.getUserName());
                uservo.setUserCount(user.getUserCount());
                uservo.setGender(user.getGender());
                uservo.setBirth(user.getBirth());
                uservo.setUserEmail(user.getUserEmail());
                uservo.setUserPhone(user.getUserPhone());
                uservo.setDescription(user.getDescription());
                uservo.setUserState(user.getUserState());
                uservo.setCreateTime(user.getCreateTime());
                uservo.setGender(user.getGender());
                if(user.getBirth() == null){
                    uservo.setAge(null);
                }else{
                    try {
                        uservo.setAge(GetAgeByBirthUtils.getAgeByBirth(user.getBirth()));
                    } catch (ParseException e) {
                        ExceptionCast.cast(CommonCode.BIRTH_ERROR);
                    }
                }
                userVos.add(uservo);
           // }
        }
        //解析分页结果
        PageInfo<UserVo> pageInfo = new PageInfo<>(pag.getResult());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(userVos);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);

    }

    public UserVo getUserVoByUserId(String user_id) {
        //1先查id 找到user对象
        User user = this.getUserById(user_id);
        String roleId=user.getRoleId();
        Role role = new Role();
        role.setRoleId(roleId);
        //List<UserVo> userVos = new ArrayList<UserVo>();
        //座位号 根据userid查座位号查询
        //List<Seat> seatByUserId = seatService.getSeatByUserId(user.getUserId());
        //for(Seat seat1 : seatByUserId) {
            UserVo uservo = new UserVo();
            //uservo.setSeatNum(seat1.getSeatNum());
            uservo.setRoleId(user.getRoleId());
            uservo.setUserId(user.getUserId());
            uservo.setRoleName(roleMapper.selectOne(role).getRoleName());
            uservo.setUserName(user.getUserName());
            uservo.setUserCount(user.getUserCount());
            uservo.setGender(user.getGender());
            if (user.getBirth() == null) {
                uservo.setAge(null);
            } else {
                try {
                    uservo.setAge(GetAgeByBirthUtils.getAgeByBirth(user.getBirth()));
                } catch (ParseException e) {
                    ExceptionCast.cast(CommonCode.BIRTH_ERROR);
                }
            }
            uservo.setUserGrade(user.getUserGrade());
            uservo.setBirth(user.getBirth());
            uservo.setUserEmail(user.getUserEmail());
            uservo.setUserPhone(user.getUserPhone());
            uservo.setDescription(user.getDescription());
            uservo.setUserState(user.getUserState());
            uservo.setCreateTime(user.getCreateTime());
            uservo.setGender(user.getGender());
            //userVos.add(uservo);
        //}

        //return userVos;
        return uservo;
    }

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
            //清空座位表对应的信息??  修改seat->userId设置为空
            List<Seat> seatByUserId = seatService.getSeatByUserId(userId);
            for(Seat seat : seatByUserId){
                //SeatVo seatVo = new SeatVo();
                //seatVo.setUserId(seat.getUserId());
                //seatVo.setSeatCount(this.getUserById(seat.getUserId()).getUserCount());
                //seatVo.setSeatNum(seat.getSeatNum());
                Example example =new Example(Seat.class);
                Example.Criteria criteria = example.createCriteria();
                seat.setUserId(null);
                criteria.andEqualTo("seatId",seat.getSeatId());
                int upd= seatMapper.updateByExample(seat,example);   //修改的部分值组成的对象
                if(upd != 1){
                    ExceptionCast.cast(CommonCode.UPDATE_FAIL);
                }
            }
            //删除签到信息
            signService.deleteSignByUserId(userId);
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
        user.setUserState(0);//默认在
        user.setCreateTime(new Date());
        int ins = userMapper.insert(user);
        if(ins != 1){
            ExceptionCast.cast(CommonCode.INSERT_FAIL);
        }
    }


    /*
    * update基本信息
    * */
    @Transactional
    public void updateUser(UserVo uservo){
        //User user = new User();
        User userById = this.getUserById(uservo.getUserId());
        //账号唯一性
        if(!(uservo.getUserCount()).equals(userById.getUserCount())){
            if(this.getUserByCount(uservo.getUserCount()) == null){
                uservo.setUserCount(uservo.getUserCount());
            }
        }
        Example example =new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",uservo.getUserId());
        int upd= userMapper.updateByExampleSelective(uservo,example);
        if(upd != 1){
            ExceptionCast.cast(CommonCode.UPDATE_FAIL);
        }
    }

    /*更改密码 */
    @Transactional
    //@CacheEvict(value="LabUser",allEntries=true)
    public void updatePass(String id,String OldPass,String NewPass){
        User user = this.getUserById(id);
        //判断旧密码
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean f = passwordEncoder.matches(OldPass, user.getUserPass());
        if(f){
            String newPa = null;
            try {
                newPa = MyPassword.PasswrodEncoder(NewPass);
            } catch (Exception e) {
                ExceptionCast.cast(CommonCode.PASSWORD_ERROR);
            }
            user.setUserPass(newPa);
            Example example =new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userId",id);
            int upd= userMapper.updateByExampleSelective(user,example);
            if(upd != 1){
                ExceptionCast.cast(CommonCode.UPDATE_FAIL);
            }
        }else{
            ExceptionCast.cast(CommonCode.PASSWORD_OLD_ERROR);
        }

    }

}
