package com.hc.learning.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.common.utils.TrainingResult;
import com.hc.learning.entity.Teacher;
import com.hc.learning.entity.vo.TeacherQuery;
import com.hc.learning.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-02-21
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/{page}/{limit}")
    @ApiOperation("条件分页查询讲师数据")
    public TrainingResult getPageTeacher(@PathVariable(value = "page") Long page,
                                        @PathVariable(value = "limit") Long limit,
                                        @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<Teacher> teacherPage = new Page<>(page, limit);
        teacherService.pageQuery(teacherPage, teacherQuery);
        long total = teacherPage.getTotal();
        List<Teacher> rows = teacherPage.getRecords();
        return TrainingResult.ok().data("total", total).data("rows", rows);
    }

    @GetMapping("/all")
    @ApiOperation("获取所有讲师数据")
    public TrainingResult getAllTeacher() {
        List<Teacher> teacherList = teacherService.list(null);
        return TrainingResult.ok().data("teacherList", teacherList);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取讲师信息")
    public TrainingResult getTeacherById(@PathVariable(value = "id") String id) {
        Teacher teacher = teacherService.getById(id);
        return TrainingResult.ok().data("teacher", teacher);
    }

    @PostMapping("/add")
    @ApiOperation("添加讲师")
    public TrainingResult addTeacher(@RequestBody Teacher teacher) {
        boolean success = teacherService.save(teacher);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器异常，添加失败！");
    }

    @PostMapping("{id}")
    @ApiOperation("根据id更新讲师信息")
    public TrainingResult updateTeacher(@RequestBody Teacher teacher) {
        boolean success = teacherService.updateById(teacher);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器异常，修改失败！");
    }

    @DeleteMapping("{id}")
    @ApiOperation("根据id删除讲师")
    public TrainingResult deleteTeacher(@PathVariable(value = "id") String id) {
        boolean success = teacherService.removeById(id);
        return success ? TrainingResult.ok() : TrainingResult.error().message("删除失败！");
    }

}

