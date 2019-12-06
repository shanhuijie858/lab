package com.lsy.lab.response;

import lombok.ToString;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 10:43
 * @description: 响应状态信息
 */
@ToString
public enum CommonCode implements ResultCode{
    INVALID_PARAM(false,10003,"非法参数！"),
    SUCCESS(true,10000,"操作成功！"),
    FAIL(false,11111,"操作失败！"),
    UNAUTHENTICATED(false,10001,"此操作需要登陆系统！"),
    UNAUTHORISE(false,10002,"权限不足，无权操作！"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),

    DATA_ERROR(false, 301, "前台数据有误！"),
    USER_NOT_EXIST(false,302,"用户不存在"),
    USER_IS_EXISTS(false,303,"用户已存在"),
    DATA_IS_NULL(false,304,"数据为空"),
    DELETE_FAIL(false,305,"删除失败"),
    UPDATE_FAIL(false,306,"更新失败"),
    VERIFY_FAIL(false,307,"验证失败"),
    INSERT_FAIL(false,308,"添加失败"),
    SELECT_NULL(false,311,"查询失败"),
    DELETE_USER_NOT_EXIST(false,312,"删除的用户不存在"),
    COUNT_EXIST(false,313,"账号已存在"),
    PASSWORD_ERROR(false,318,"密码错误"),
    PASSWORD_OLD_ERROR(false,319,"原密码输入错误"),

    ;
    //    private static ImmutableMap<Integer, CommonCode> codes ;
    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private CommonCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }
    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}

