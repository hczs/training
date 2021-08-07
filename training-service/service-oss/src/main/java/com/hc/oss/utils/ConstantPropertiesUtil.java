package com.hc.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 阿里云配置信息获取util
 * 使用@Value读取application.properties中的内容
 * 使用spring的InitializingBean的afterPropertiesSet来初始化配置信息
 * @author hczs8
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.endpoint}")
    private String endPoint;

    @Value("${aliyun.keyid}")
    private String keyId;

    @Value("${aliyun.keysecret}")
    private String keySecret;

    @Value("${aliyun.bucketname}")
    private String bucketName;

    public static String ENDPOINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    /**
     * 初始化属性信息
     */
    @Override
    public void afterPropertiesSet() {
        ENDPOINT = endPoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
