package com.hc.video.controller;

import com.hc.common.utils.TrainingResult;
import com.hc.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author ：hc
 * @date ：Created in 2021/2/28 19:07
 * @modified By：
 */
@RestController
@RequestMapping("/vod")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    @ApiOperation("视频上传")
    public TrainingResult uploadVideo(MultipartFile file) {
        String videoSourceId = videoService.uploadVideo(file);
        return TrainingResult.ok().data("videoSourceId", videoSourceId);
    }

    @GetMapping("/{videoSourceId}")
    @ApiOperation("根据云端videoId来获取视频时长及大小信息")
    public TrainingResult getVideoInfo(@PathVariable(value = "videoSourceId") String videoSourceId) {
        Map<String, Object> infoMap = videoService.getVideoInfo(videoSourceId);
        Float duration = (Float) infoMap.get("duration");
        Long size = (Long) infoMap.get("size");
        return TrainingResult.ok().data("duration", duration).data("size", size);
    }

    @GetMapping("/playAuth/{videoSourceId}")
    @ApiOperation("获取视频播放凭证")
    public TrainingResult getPlayAuthById(@PathVariable(value = "videoSourceId") String videoSourceId) {
        String auth = videoService.getPlayAuthById(videoSourceId);
        return TrainingResult.ok().data("playAuth", auth);
    }

    @DeleteMapping("/{videoSourceId}")
    @ApiOperation("删除云端视频")
    public TrainingResult deleteVideo(@PathVariable(value = "videoSourceId") String videoSourceId) {
        boolean success = videoService.deleteVideo(videoSourceId);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误！删除失败！");
    }
}
