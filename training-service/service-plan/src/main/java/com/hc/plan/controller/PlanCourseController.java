package com.hc.plan.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.hc.common.utils.TrainingResult;
import com.hc.plan.entity.Course;
import com.hc.plan.service.PlanCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/plan-course")
public class PlanCourseController {

    @Autowired
    private PlanCourseService planCourseService;

    @GetMapping("/courses/{planId}")
    @ApiOperation("获取指定培训计划下的所有课程")
    public TrainingResult getCoursesByPlanId(@PathVariable("planId") String planId) {
        List<Course> courses = planCourseService.getCoursesByPlanId(planId);
        return TrainingResult.ok().data("courses", courses);
    }
}

