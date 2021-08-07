package com.hc.plan.mapper;

import com.hc.plan.entity.Course;
import com.hc.plan.entity.PlanCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author houcheng
 * @since 2021-03-26
 */
public interface PlanCourseMapper extends BaseMapper<PlanCourse> {


    /**
     * 获取指定培训计划下的所有课程
     * @param planId 计划id
     * @return 课程List
     */
    List<Course> getCoursesByPlanId(String planId);

}
