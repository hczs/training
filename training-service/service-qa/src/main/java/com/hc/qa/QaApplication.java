package com.hc.qa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ：hc
 * @date ：Created in 2021/4/2 18:28
 * @modified By：
 */
@SpringBootApplication
@EnableFeignClients
@ComponentScan("com.hc")
@MapperScan("com.hc.qa.mapper")
public class QaApplication {
    public static void main(String[] args) {
        SpringApplication.run(QaApplication.class, args);
    }
}
