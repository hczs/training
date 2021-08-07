package com.hc.learning.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc.common.exception.TrainingException;
import com.hc.common.utils.JwtUtils;
import com.hc.common.utils.ResultStatus;
import com.hc.common.utils.TrainingResult;
import com.hc.learning.client.UcenterClient;
import com.hc.learning.entity.Course;
import com.hc.learning.entity.Staff;
import com.hc.learning.entity.vo.CourseQuery;
import com.hc.learning.entity.vo.TreeChapter;
import com.hc.learning.entity.vo.front.CourseInfoFrontVo;
import com.hc.learning.service.ChapterService;
import com.hc.learning.service.CourseService;
import com.hc.learning.service.StaffCourseService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.ognl.EnumerationIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author ：hc
 * @date ：Created in 2021/3/4 19:43
 */
@RestController
@RequestMapping("/front/course")
public class CourseFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @PostMapping("/{page}/{limit}")
    @ApiOperation("条件分页查询课程数据")
    public TrainingResult getAllCourse(@PathVariable(value = "page") Integer page,
                                       @PathVariable(value = "limit") Integer limit,
                                       @RequestBody(required = false) CourseQuery courseQuery) {
        Page<Course> coursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.courseFrontQuery(coursePage, courseQuery);
        return TrainingResult.ok().data(map);
    }

    @GetMapping("/hot")
    @ApiOperation("获取热门课程，前四个，参考字段学习人数，浏览量")
    public TrainingResult getHotCourse() {
        return TrainingResult.ok().data("rows", courseService.getHotCourses());
    }

    @GetMapping("{id}")
    @ApiOperation("获取课程详情信息")
    public TrainingResult getCourseInfo(@PathVariable(value = "id") String id, HttpServletRequest request) {
        try {
            // 检查是否登录，有可能token会过期
            String staffId = JwtUtils.getStaffIdByJwtToken(request);
            if (null == staffId || staffId.length() == 0) {
                return TrainingResult.ok().status(ResultStatus.UNAUTHORIZED.getCode()).message("请先登录！");
            }
            CourseInfoFrontVo frontVo = courseService.getCourseInfoFrontVoById(id);
            // 还要封装章节信息
            List<TreeChapter> treeChapter = chapterService.getTreeChapter(staffId, id);
            return TrainingResult.ok().data("courseInfo", frontVo).data("chapterInfo", treeChapter);
        } catch (ExpiredJwtException e) {
            return TrainingResult.ok().status(ResultStatus.UNAUTHORIZED.getCode()).message("身份验证过期，请重新登录！");
        }


    }

    @GetMapping("/join/{courseId}")
    @ApiOperation("参与课程")
    public TrainingResult joinCourse(HttpServletRequest request, @PathVariable(value = "courseId") String courseId) {
        try {
            // 检查是否登录，有可能token会过期
            String staffId = JwtUtils.getStaffIdByJwtToken(request);
            if (null == staffId || staffId.length() == 0) {
                return TrainingResult.ok().status(ResultStatus.UNAUTHORIZED.getCode()).message("请先登录！");
            }
            // 已登录就正常添加记录
            boolean success = courseService.joinCourse(staffId, courseId);
            return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，参与课程失败！");
        } catch (ExpiredJwtException e) {
            return TrainingResult.ok().status(ResultStatus.UNAUTHORIZED.getCode()).message("身份验证过期，请重新登录！");
        }
    }
}
