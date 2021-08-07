package com.hc.video.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author ：hc
 * @date ：Created in 2021/2/28 19:07
 * @modified By：
 */
public interface VideoService {
    /**
     * 上传视频文件到阿里云服务器
     * @param file 视频文件
     * @return  视频id
     */
    String uploadVideo(MultipartFile file);

    /**
     * 根据云端视频id获取视频的时长及大小信息
     * @param videoSourceId 云端视频id
     * @return 视频信息map集合
     */
    Map<String, Object> getVideoInfo(String videoSourceId);

    /**
     * 获取视频播放凭证
     * @param id 视频videoSourceId
     * @return 视频播放凭证
     */
    String getPlayAuthById(String id);

    /**
     * 根据云端视频id删除视频
     * @param videoSourceId 云端视频id
     * @return 是否成功
     */
    boolean deleteVideo(String videoSourceId);
}
