package com.lsy.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Shan HuiJie
 * @Description:
 * @Date 2019.12.16 11:00
 */
@Data    //getter setter hashCode equals
@Table(name = "role")
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
@NameStyle(Style.normal)   //数据库字段与属性名写法保持一致
public class Role {
    @Id
    private String roleId;
    private String roleName;
    private String status;
}
