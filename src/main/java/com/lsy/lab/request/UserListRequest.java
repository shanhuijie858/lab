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
public class UserListRequest {
    //用户ID
    String user_id;

    //用户昵称
    String user_nickname;

    //用户手机号
    String user_phone;
    //角色
    String role_id;
}
