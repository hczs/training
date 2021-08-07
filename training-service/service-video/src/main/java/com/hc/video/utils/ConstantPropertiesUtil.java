package com.hc.video.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取配置信息常量类
 * 实现了InitializingBean接口，然后实现其中的afterPropertiesSet方法，从而达到初始化bean的目的
 * 初始化bean的时候都会执行afterPropertiesSet方法
 * @author ：hc
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String accessKey;
    @Value("${aliyun.vod.file.keysecret}")
    private String accessSecret;
    @Value("${aliyun.vod.file.regionId}")
    private String regionId;

    public static String ACCESS_KEY;
    public static String ACCESS_SECRET;
    public static String REGION_ID;

    @Override
    public void afterPropertiesSet() {
        ACCESS_KEY = accessKey;
        ACCESS_SECRET = accessSecret;
        REGION_ID = regionId;
    }
}
