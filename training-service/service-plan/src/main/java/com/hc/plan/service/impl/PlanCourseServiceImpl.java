package com.hc.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.plan.entity.Course;
import com.hc.plan.entity.PlanCourse;
import com.hc.plan.mapper.PlanCourseMapper;
import com.hc.plan.service.PlanCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houcheng
 * @since 2021-03-26
 */
@Service
public class PlanCourseServiceImpl extends ServiceImpl<PlanCourseMapper, PlanCourse> implements PlanCourseService {

    @Override
    public boolean removeByPlanId(String planId) {
        QueryWrapper<PlanCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("plan_id", planId);
        baseMapper.delete(wrapper);
        return true;
    }

    @Override
    public List<Course> getCoursesByPlanId(String planId) {
        return baseMapper.getCoursesByPlanId(planId);
    }
}
