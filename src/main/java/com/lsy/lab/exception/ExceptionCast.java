package com.lsy.lab.exception;


import com.lsy.lab.response.ResultCode;

/**
 * 定义异常抛出类
 */
public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}



