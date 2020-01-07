package com.lsy.lab.model.vo;

import com.lsy.lab.model.User;
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
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
public class UserVo extends User implements Serializable {

    private String roleName;  //角色名称
    private Integer age;  //年龄
   // private Integer seatNum;  //座位号

}