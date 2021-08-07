package com.hc.learning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ：hc
 * @date ：Created in 2021/2/21 14:36
 * @modified By：
 */
@SpringBootApplication
@EnableFeignClients
@ComponentScan("com.hc")
@MapperScan("com.hc.learning.mapper")
public class LearningApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningApplication.class, args);
    }
}
