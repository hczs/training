package com.hc.learning.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc.common.utils.JwtUtils;
import com.hc.common.utils.ResultStatus;
import com.hc.common.utils.TrainingResult;
import com.hc.learning.client.UcenterClient;
import com.hc.learning.entity.CourseComment;
import com.hc.learning.entity.Staff;
import com.hc.learning.service.CourseCommentService;
import com.hc.learning.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-03-17
 */
@RestController
@RequestMapping("/course-comment")
public class CourseCommentController {

    @Autowired
    private CourseCommentService courseCommentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UcenterClient ucenterClient;

    @PostMapping("/add")
    @ApiOperation("添加评论")
    public TrainingResult addComment(@RequestBody CourseComment comment, HttpServletRequest request) {
        // 先判断是否登录
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.error().status(ResultStatus.UNAUTHORIZED.getCode()).message("请先登录！");
        }
        // 获取用户信息
        TrainingResult result = ucenterClient.getUserInfo(staffId);
        if (result.getSuccess()) {
            Map<String, Object> data = result.getData();
            Object staffInfo = data.get("staffInfo");
            ObjectMapper objectMapper = new ObjectMapper();
            Staff staff = objectMapper.convertValue(staffInfo, Staff.class);
            // 设置评论中员工相关信息
            comment.setStaffId(staff.getId());
            comment.setAvatar(staff.getAvatar());
            comment.setUsername(staff.getUsername());
            boolean success = courseCommentService.save(comment);
            // 评论数+1
            boolean commentSuccess = courseService.updateCommentNum(comment.getCourseId());
            return success && commentSuccess ? TrainingResult.ok() : TrainingResult.error().message("服务器错误！发表评论失败！");
        } else {
            return result;
        }
    }

    @GetMapping("/{page}/{limit}")
    @ApiOperation("分页查询评论信息")
    public TrainingResult getCommentPage(@PathVariable(value = "page") Integer page,
                                     @PathVariable(value = "limit") Integer limit,
                                     String courseId) {
        // 执行条件分页查询
        Page<CourseComment> commentPage = new Page<>(page, limit);
        QueryWrapper<CourseComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        courseCommentService.page(commentPage, wrapper);
        // 设置查询结果相关参数，返回map
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", commentPage.getRecords());
        map.put("total", commentPage.getTotal());
        map.put("current", commentPage.getCurrent());
        map.put("size", commentPage.getSize());
        return TrainingResult.ok().data(map);
    }
}

