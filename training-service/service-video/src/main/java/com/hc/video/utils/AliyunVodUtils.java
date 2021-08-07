package com.hc.video.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 阿里云视频上传sdk工具类
 * @author ：hc
 */
public class AliyunVodUtils {

    /**
     * 初始化阿里云视频点播服务client对象
     * @param regionId 视频点播服务接入区域id
     * @param accessKeyId accessKeyId
     * @param accessKeySecret accessKeySecret
     * @return DefaultAcsClient
     * @throws ClientException
     */
    public static DefaultAcsClient initVodClient(String regionId, String accessKeyId, String accessKeySecret) throws ClientException {
        // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
