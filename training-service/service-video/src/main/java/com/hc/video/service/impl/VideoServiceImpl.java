package com.hc.video.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;
import com.hc.common.exception.TrainingException;
import com.hc.video.service.VideoService;
import com.hc.video.utils.AliyunVodUtils;
import com.hc.video.utils.ConstantPropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：hc
 * @date ：Created in 2021/2/28 19:07
 */
@Service
public class VideoServiceImpl implements VideoService {

    public static final Logger logger = LoggerFactory.getLogger(VideoService.class);

    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String title = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            // 创建请求对象
            UploadStreamRequest uploadStreamRequest = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY,
                    ConstantPropertiesUtil.ACCESS_SECRET,
                    title, originalFilename, inputStream);
            // 上传视频使用UploadVideoImpl
            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse uploadStreamResponse = uploadVideo.uploadStream(uploadStreamRequest);
            // 先获取videoId
            String videoId = uploadStreamResponse.getVideoId();
            // 不成功就返回错误信息
            if (!uploadStreamResponse.isSuccess()) {
                String errorMessage = "上传视频失败！错误码：" + uploadStreamResponse.getCode() + "返回信息：" + uploadStreamResponse.getMessage();
                // 本地日志打印
                logger.warn(errorMessage);
                // 如果id都没有，那返回异常
                if (!StringUtils.isEmpty(videoId)) {
                    throw new TrainingException(500, errorMessage);
                }
            }
            Map<String, Object> videoInfo = getVideoInfo(videoId);

            return videoId;
        } catch (Exception e) {
            throw new TrainingException(500, "服务器错误！视频上传失败！");
        }
    }

    @Override
    public Map<String, Object> getVideoInfo(String videoSourceId) {
        try {
            DefaultAcsClient vodClient = AliyunVodUtils.initVodClient(
                    ConstantPropertiesUtil.REGION_ID,
                    ConstantPropertiesUtil.ACCESS_KEY,
                    ConstantPropertiesUtil.ACCESS_SECRET);
            GetVideoInfoRequest infoRequest = new GetVideoInfoRequest();
            infoRequest.setVideoId(videoSourceId);
            GetVideoInfoResponse infoResponse = vodClient.getAcsResponse(infoRequest);
            HashMap<String, Object> map = new HashMap<>();
            map.put("duration", infoResponse.getVideo().getDuration());
            map.put("size", infoResponse.getVideo().getSize());
            return map;
        } catch (Exception e) {
            throw new TrainingException(500, "服务器错误！获取视频信息失败！");
        }
    }

    @Override
    public String getPlayAuthById(String id) {
        try {
            // 创建client对象
            DefaultAcsClient client = AliyunVodUtils.initVodClient(ConstantPropertiesUtil.REGION_ID,ConstantPropertiesUtil.ACCESS_KEY, ConstantPropertiesUtil.ACCESS_SECRET);
            // request设置id
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            // 获取response，获取凭证
            GetVideoPlayAuthResponse acsResponse = client.getAcsResponse(request);
            return acsResponse.getPlayAuth();
        } catch (Exception e) {
            throw new TrainingException(500, "获取视频播放凭证失败！");
        }
    }

    @Override
    public boolean deleteVideo(String videoSourceId) {
        try {
            DefaultAcsClient client = AliyunVodUtils.initVodClient(ConstantPropertiesUtil.REGION_ID,
                    ConstantPropertiesUtil.ACCESS_KEY, ConstantPropertiesUtil.ACCESS_SECRET);
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            deleteVideoRequest.setVideoIds(videoSourceId);
            client.getAcsResponse(deleteVideoRequest);
            return true;
        } catch (Exception e) {
            throw new TrainingException(500,"服务器错误！视频删除失败！");
        }
    }
}
