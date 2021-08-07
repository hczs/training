package com.hc.plan.service;

import com.hc.plan.entity.Course;
import com.hc.plan.entity.PlanCourse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houcheng
 * @since 2021-03-26
 */
public interface PlanCourseService extends IService<PlanCourse> {

    /**
     * 根据计划id删除计划课程表中的相应数据
     * @param planId 计划id
     * @return 是否成功
     */
    boolean removeByPlanId(String planId);

    /**
     * 获取指定培训计划下的所有课程
     * @param planId 计划id
     * @return 课程List
     */
    List<Course> getCoursesByPlanId(String planId);

}
