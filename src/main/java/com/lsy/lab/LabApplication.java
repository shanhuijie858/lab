package com.lsy.lab;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;
/**
 * @author wzh
 */

@SpringBootApplication
@MapperScan("com.lsy.lab.mapper")
@EntityScan("com.lsy.lab.model")//扫描实体类
@EnableSwagger2
public class LabApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabApplication.class, args);
    }

}