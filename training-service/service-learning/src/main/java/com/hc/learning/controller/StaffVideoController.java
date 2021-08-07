package com.hc.learning.controller;


import com.hc.common.utils.JwtUtils;
import com.hc.common.utils.ResultStatus;
import com.hc.common.utils.TrainingResult;
import com.hc.learning.service.StaffVideoService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-03-11
 */
@RestController
@RequestMapping("/staff-video")
public class StaffVideoController {

    @Autowired
    private StaffVideoService staffVideoService;

    @GetMapping("/reocrd/{videoId}")
    @ApiOperation("记录员工观看小节的总时长")
    public TrainingResult recordAllTime(@PathVariable(value = "videoId") String videoId,
                                        @RequestParam(value = "allTime") Float allTime,
                                        HttpServletRequest request) {
        // 先检查是否登录，防止登录状态失效导致学习时长没有记录上
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.error().status(ResultStatus.UNAUTHORIZED.getCode()).message("登录状态失效，请重新登录！");
        } else {
            boolean success = staffVideoService.recordAllTime(staffId, videoId, allTime);
            return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，学习时长记录失败！");
        }
    }

    @GetMapping("/last/{videoId}")
    @ApiOperation("记录员工观看小节上次播放记录")
    public TrainingResult lastVideoTime(@PathVariable(value = "videoId") String videoId,
                                        @RequestParam(value = "lastTime") Float lastTime,
                                        HttpServletRequest request) {
        // 先检查是否登录，防止登录状态失效导致播放记录未保存
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.error().status(ResultStatus.UNAUTHORIZED.getCode()).message("登录状态失效，请重新登录！");
        } else {
            boolean success = staffVideoService.recordLastTime(staffId, videoId, lastTime);
            return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，播放记录失败！");
        }
    }

    @GetMapping("/progress/{videoId}")
    @ApiOperation("获取员工上次播放进度")
    public TrainingResult getLastTime(@PathVariable(value = "videoId") String videoId,
                                      HttpServletRequest request) {
        // 先检查是否登录，防止登录状态失效导致播放记录未保存
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.error().status(ResultStatus.UNAUTHORIZED.getCode()).message("登录状态失效，请重新登录！");
        } else {
            Float lastTime = staffVideoService.getLastTime(staffId, videoId);
            // 不为空就返回具体时间，为空（以前没看过，查不到记录）就从0开始看（返回0）
            return lastTime != null ? TrainingResult.ok().data("lastTime", lastTime) : TrainingResult.ok().data("lastTime", 0);
        }
    }

    @GetMapping("/accomplish/{courseId}/{videoId}")
    @ApiOperation("小节观看完毕设置小节完成字段为1")
    public TrainingResult accomplishVideo(@PathVariable("videoId") String videoId,
                                          @PathVariable("courseId") String courseId,
                                          HttpServletRequest request) {
        // 先检查是否登录，防止登录状态失效导致观看未完成
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.error().status(ResultStatus.UNAUTHORIZED.getCode()).message("登录状态失效，请重新登录！");
        } else {
            boolean success = staffVideoService.accomplishVideo(courseId, videoId, staffId);
            return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，小节观看完成记录失败！");
        }
    }

    @GetMapping("/time/{staffId}")
    @ApiOperation("获取指定员工学习总时长")
    public TrainingResult getLearningTime(@PathVariable("staffId") String staffId) {
        return TrainingResult.ok().data("learningTime", staffVideoService.getLearning(staffId));
    }
}

