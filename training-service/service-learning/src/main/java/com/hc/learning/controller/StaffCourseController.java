package com.hc.learning.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hc.common.utils.JwtUtils;
import com.hc.common.utils.ResultStatus;
import com.hc.common.utils.TrainingResult;
import com.hc.learning.entity.StaffCourse;
import com.hc.learning.entity.vo.JoinCourseVo;
import com.hc.learning.service.CourseService;
import com.hc.learning.service.StaffCourseService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-03-11
 */
@RestController
@RequestMapping("/staff-course")
public class StaffCourseController {

    @Autowired
    private StaffCourseService staffCourseService;

    @GetMapping("/study/{courseId}")
    @ApiOperation("查询指定用户是否参与了本课程的学习")
    public TrainingResult isStudyCourse(@PathVariable(value = "courseId") String courseId,
                                        @RequestParam(value = "staffId", required = false) String staffId) {
        boolean result = staffCourseService.isStudyCourse(staffId, courseId);
        return TrainingResult.ok().data("isStudy", result);
    }

    @GetMapping("/{page}/{limit}")
    @ApiOperation("查询指定用户参与的课程信息")
    public TrainingResult getJoinCourse(HttpServletRequest request,
                                        @PathVariable(value = "page") Long page,
                                        @PathVariable(value = "limit") Long limit) {
        // 判断是否登录
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.ok().status(ResultStatus.UNAUTHORIZED.getCode()).message("请先登录！");
        } else {
            Page<JoinCourseVo> pageParam = new Page<>(page, limit);
            staffCourseService.getJoinCourse(pageParam, staffId);
            return TrainingResult.ok().data("total", pageParam.getTotal()).data("rows", pageParam.getRecords());
        }
    }

    @GetMapping("/progress/{staffId}/{courseId}")
    @ApiOperation("获取指定用户观看指定课程的播放进度(未测试)")
    public TrainingResult getPercent(@PathVariable("staffId") String staffId,
                                     @PathVariable("courseId") String courseId) {
        Double percentage = staffCourseService.getPercentage(staffId, courseId);
        return TrainingResult.ok().data("percentage", percentage);
    }
}

