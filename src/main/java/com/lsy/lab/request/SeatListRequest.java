package com.lsy.lab.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Shan HuiJie
 * @Description: 暴露给其他人，用来获取user信息
 * @Date 2019.10.29 17:27
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SeatListRequest {
    private  Integer seatNum; //座位编号
    private String userName;  //姓名
    private String userCount; //工号学号
}
