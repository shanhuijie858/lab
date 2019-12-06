package com.lsy.lab.model.vo;

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
@Table(name = "member_checkin")
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.normal)
public class SignVo implements Serializable {
    private String signId; //签到id
    private String userCount; //用户工号
    private String userName;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date signTime; //签到时间


}
