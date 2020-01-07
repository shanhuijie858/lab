package com.lsy.lab.controller;

import com.lsy.lab.api.SeatControllerApi;
import com.lsy.lab.model.Seat;
import com.lsy.lab.model.Sign;
import com.lsy.lab.model.vo.SeatVo;
import com.lsy.lab.model.vo.UserVo;
import com.lsy.lab.request.SeatListRequest;
import com.lsy.lab.response.CommonCode;
import com.lsy.lab.response.QueryResponseResult;
import com.lsy.lab.response.ResponseResult;
import com.lsy.lab.service.SeatService;
import com.lsy.lab.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author wzh
 */

@RestController
@RequestMapping("/lab/seat")
@Slf4j
public class SeatController implements SeatControllerApi {

    @Autowired
    private SeatService seatService;
    @Override
    @GetMapping("/getAllSeats/{page}/{size}")
    public QueryResponseResult getAllSeats(@PathVariable("page") Integer page,
                                            @PathVariable("size") Integer size,
                                           SeatListRequest seatListRequest) {
        return seatService.getAllSeat(page,size,seatListRequest);
    }

    @Override
    @PutMapping("/update")
    public ResponseResult update(@RequestBody SeatVo seatVo){
        seatService.updateSeat(seatVo);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @PostMapping ("/insert")
    public ResponseResult insertUser(@RequestBody @Valid Seat seat) {
        seatService.insertSeat(seat);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    @Override
    @DeleteMapping("/delete/seatId/{id}")
    public ResponseResult deleteSeatById(@PathVariable("id") String id){
        seatService.deleteSeatById(id);
        return  new ResponseResult(CommonCode.SUCCESS);
    }
    @Override
    @DeleteMapping("/delete/userCount/{count}")
    public ResponseResult deleteSeatByUserCount(@PathVariable("count") String count){
        seatService.deleteSeatByCount(count);
        return  new ResponseResult(CommonCode.SUCCESS);
    }

}