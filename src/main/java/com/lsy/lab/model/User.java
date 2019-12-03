package com.lsy.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import java.io.Serializable;

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
public class User implements Serializable {
    @Id //声明主键字段
    private String id; //会员id
    private String name;






}