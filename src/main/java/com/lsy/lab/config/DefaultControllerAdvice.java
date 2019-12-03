package com.lsy.lab.config;

import com.lsy.lab.result.Result;
import com.lsy.lab.result.ResultEnum;
import com.lsy.lab.result.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author wzh
 */


@ControllerAdvice
@ResponseBody
public class DefaultControllerAdvice {

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler() {

        return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
    }
}
