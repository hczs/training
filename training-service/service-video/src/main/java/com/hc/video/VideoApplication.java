package com.hc.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author ：hc
 * @date ：Created in 2021/2/28 18:17
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class VideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class, args);
    }
}
