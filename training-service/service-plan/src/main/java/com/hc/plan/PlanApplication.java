package com.hc.plan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ：hc
 * @date ：Created in 2021/3/24 18:59
 */
@SpringBootApplication
@ComponentScan("com.hc")
@MapperScan("com.hc.plan.mapper")
public class PlanApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanApplication.class);
    }
}
