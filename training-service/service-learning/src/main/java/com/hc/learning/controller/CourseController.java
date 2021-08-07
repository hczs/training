package com.hc.learning.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.common.utils.TrainingResult;
import com.hc.learning.entity.Course;
import com.hc.learning.entity.vo.CoursePublishVo;
import com.hc.learning.entity.vo.CourseQuery;
import com.hc.learning.entity.vo.CourseVo;
import com.hc.learning.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-02-24
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @GetMapping("{id}")
    @ApiOperation("根据id获取课程基本信息")
    public TrainingResult getCourseInfo(@PathVariable(value = "id") String id) {
        Course course = courseService.getById(id);
        return TrainingResult.ok().data("course", course);
    }

    @GetMapping("/all")
    @ApiOperation("获取所有课程数据")
    public TrainingResult getAllCourse() {
        // 默认查询已发布的
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("status","Normal");
        List<Course> list = courseService.list(wrapper);
        return TrainingResult.ok().data("courseList", list);
    }

    @PostMapping("/{page}/{limit}")
    @ApiOperation("条件分页查询课程数据")
    public TrainingResult pageCourse(@PathVariable(value = "page") Long page,
                                     @PathVariable(value = "limit") Long limit,
                                     @RequestBody(required = false) CourseQuery courseQuery) {
        Page<Course> coursePage = new Page<>(page, limit);
        courseService.pageQuery(coursePage, courseQuery);
        return TrainingResult.ok().data("rows", coursePage.getRecords()).data("total", coursePage.getTotal());
    }

    @GetMapping("/publish/info/{id}")
    @ApiOperation("根据课程id获取课程发布信息")
    public TrainingResult getCourseInfoPublish(@PathVariable(value = "id") String id) {
        CoursePublishVo coursePublishInfo = courseService.getCoursePublishInfo(id);
        return TrainingResult.ok().data("coursePublish", coursePublishInfo);
    }

    @PostMapping("/add")
    @ApiOperation("添加课程基本信息")
    public TrainingResult addCourse(@RequestBody CourseVo courseVo) {
        String courseId = courseService.addCourse(courseVo);
        return TrainingResult.ok().data("courseId", courseId);
    }

    @GetMapping("/publish/{id}")
    @ApiOperation("发布课程")
    public TrainingResult publishCourse(@PathVariable(value = "id") String id) {
        boolean success = courseService.publishCourse(id);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误！发布失败！");
    }

    @GetMapping("/publish/save/{id}")
    @ApiOperation("暂存待发布课程")
    public TrainingResult savePublishCourse(@PathVariable(value = "id") String id) {
        boolean success = courseService.savePublishCourse(id);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误！暂存失败！");
    }

    @PostMapping("/update")
    @ApiOperation("更新课程基本信息")
    public TrainingResult updateCourse(@RequestBody CourseVo courseVo) {
        boolean success = courseService.updateCourse(courseVo);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误！更新失败！");
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除课程信息")
    public TrainingResult deleteCourse(@PathVariable(value = "id") String id) {
        boolean success = courseService.deleteCourse(id);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误！课程删除失败！");

    }
}

