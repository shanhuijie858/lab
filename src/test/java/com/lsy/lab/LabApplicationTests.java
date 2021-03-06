package com.lsy.lab;

import com.lsy.lab.model.vo.SeatVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class LabApplicationTests {

    @Test
    void contextLoads() {
        SeatVo seatVo = new SeatVo();
        seatVo.setUserName("000");
        //System.out.println(seatVo.getUserId());
        System.out.println(seatVo);
    }

  @Test
    public void testPasswrodEncoder(){
        String password = "string";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //使用bcrypt加密,每次加密使用一个随机盐
        for(int i=0;i<10;i++) {
            //每个计算出的Hash值都不一样
            String hashPass = passwordEncoder.encode(password);
            System.out.println(hashPass);
            //虽然每次计算的密码Hash值不一样但是校验是通过的
            boolean f = passwordEncoder.matches(password, hashPass);
            System.out.println(f);
        }
    }

}
