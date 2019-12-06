package com.lsy.lab.model.vo;

import com.lsy.lab.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Shan HuiJie
 * @Description: 座位表
 * @Date 2019.12.03 14:53
 */
@Data    //getter setter hashCode equals
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
public class SeatVo implements Serializable {
    /*private  String seatId;
    private  String userId;
    private  String userName; //用户姓名*/
    private  Integer seatNum; //座位编号
    private  String userName;


}
