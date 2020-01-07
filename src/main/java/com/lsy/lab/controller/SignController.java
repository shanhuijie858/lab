package com.lsy.lab.controller;

import com.lsy.lab.api.SignControllerApi;
import com.lsy.lab.model.Sign;
import com.lsy.lab.response.CommonCode;
import com.lsy.lab.response.QueryResponseResult;
import com.lsy.lab.response.QueryResult;
import com.lsy.lab.response.ResponseResult;
import com.lsy.lab.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Shan HuiJie
 * @Description: 签到
 * @Date 2019.10.25 10:00
 */
@RestController
@RequestMapping("/lab/Sign")
public class SignController implements SignControllerApi {
    @Autowired
    private SignService signService;

    @Override
    @GetMapping("/getAllCheckin/{page}/{size}")
    public QueryResponseResult findAllSign(@PathVariable("page") Integer page,
                                              @PathVariable("size") Integer size,
                                              @RequestParam(value = "userCount") String userCount) {
        return signService.findAllSigns(page,size,userCount);
    }
    @Override
    @GetMapping("/find/id/{page}/{size}/{user_id}")
    public QueryResponseResult findSignByUserId(@PathVariable("user_id") String user_id,
                                                   @PathVariable("page") Integer page,
                                                   @PathVariable("size") Integer size) {
        QueryResult signById= signService.getSignByUserId(user_id,page,size);
        return new QueryResponseResult(CommonCode.SUCCESS, signById);
    }

    @Override
    @PostMapping("/insert")
    public ResponseResult insertSign(@RequestBody @Valid Sign sign) {
        signService.insertSign(sign);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    @Override
    @DeleteMapping(value = "/deleteByUserId/{userId}")
    public ResponseResult deleteSignByUserId(@PathVariable("userId") String userId) {
        signService.deleteSignByUserId(userId);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    @Override
    @DeleteMapping(value = "/deleteBySignId/{signId}")
    public ResponseResult deleteBySignId(@PathVariable("signId") String signId){
        signService.deleteBySignId(signId);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
