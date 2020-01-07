package com.lsy.lab.model.vo;

import com.lsy.lab.model.Sign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 签到信息
 * @Date
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignVo extends Sign implements Serializable {
    private String userCount; //用户工号
    private String userName; //姓名
}
