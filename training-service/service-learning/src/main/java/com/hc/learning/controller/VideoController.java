package com.hc.learning.controller;


import com.alibaba.excel.util.StringUtils;
import com.hc.common.utils.JwtUtils;
import com.hc.common.utils.ResultStatus;
import com.hc.common.utils.TrainingResult;
import com.hc.learning.client.VodClient;
import com.hc.learning.entity.Video;
import com.hc.learning.entity.vo.VideoVo;
import com.hc.learning.service.VideoService;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-02-26
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;

    @GetMapping("{id}")
    @ApiOperation("根据小节id获取小节信息")
    public TrainingResult getVideo(@PathVariable(value = "id") String id) {
        Video video = videoService.getById(id);
        return TrainingResult.ok().data("video", video);
    }

    @GetMapping("/duration/{id}")
    @ApiOperation("获取小节时长")
    public TrainingResult getDuration(@PathVariable(value = "id") String id) {
        Float duration = videoService.getDuration(id);
        return TrainingResult.ok().data("duration", duration);
    }

    @GetMapping("/playAuth/{id}")
    @ApiOperation("根据小节id获取小节视频播放凭证")
    public TrainingResult getPlayAuthById(@PathVariable("id") String id, HttpServletRequest request) {
        // 先判断是否登录，未登录不允许播放视频
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.ok().status(ResultStatus.UNAUTHORIZED.getCode()).message("请先登录！");
        } else {
            Video video = videoService.getById(id);
            TrainingResult result = vodClient.getPlayAuthById(video.getVideoSourceId());
            result.data("videoSourceId", video.getVideoSourceId());
            return result;
        }
    }

    @PostMapping("/add")
    @ApiOperation("添加小节信息")
    public TrainingResult addVideo(@RequestBody VideoVo videoVo) {
        Video video = new Video();
        BeanUtils.copyProperties(videoVo, video);
        TrainingResult videoInfo = vodClient.getVideoInfo(videoVo.getVideoSourceId());
        Map<String, Object> data = videoInfo.getData();
        Float duration =Float.parseFloat(data.get("duration").toString());
        Long size = Long.valueOf(data.get("size").toString());
        video.setDuration(duration);
        video.setSize(size);
        boolean success = videoService.save(video);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器出现错误，请重试！");
    }

    @PostMapping("/update")
    @ApiOperation("更新小节信息")
    public TrainingResult updateVideo(@RequestBody VideoVo videoVo) {
        boolean success = videoService.updateVideo(videoVo);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器出现错误，更新失败！");
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除小节信息")
    public TrainingResult deleteVideo(@PathVariable(value = "id") String id) {
        boolean success = videoService.deleteVideo(videoService.getById(id));
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器出现错误，删除失败！");
    }

    @GetMapping("/test")
    @ApiOperation("测试feign")
    public TrainingResult getVideoInfo(String videoSourceId) {
        TrainingResult videoInfo = vodClient.getVideoInfo(videoSourceId);
        Map<String, Object> data = videoInfo.getData();
        Float duration =Float.parseFloat(data.get("duration").toString());
        Long size = Long.valueOf(data.get("size").toString());
        return TrainingResult.ok().data("duration", duration).data("size", size);
    }
}

