package com.lsy.lab.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 会员实体类
 * @Date
 */

@Data    //getter setter hashCode equals
@Table(name = "user")
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
@NameStyle(Style.normal)   //数据库字段与属性名写法保持一致
public class UserVo implements Serializable {
    @Id //声明主键字段
    private String userId; //会员id
    private String roleName;
    private String userName;
    private String userPass;
    private String userCount;
    private String gender; //用户性别 0：男 1：女
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private int age;
    private Date birth; //用户出生年月
    private String userPhone;
    private String userEmail;
    private String description;
    private boolean userState;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createTime;
    private Integer seatNum;






}