package com.hc.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.common.exception.TrainingException;
import com.hc.plan.entity.Plan;
import com.hc.plan.entity.PlanCourse;
import com.hc.plan.entity.PlanStaff;
import com.hc.plan.entity.vo.PlanQuery;
import com.hc.plan.entity.vo.PlanVo;
import com.hc.plan.mapper.PlanMapper;
import com.hc.plan.service.PlanCourseService;
import com.hc.plan.service.PlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.plan.service.PlanStaffService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houcheng
 * @since 2021-03-26
 */
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements PlanService {

    @Autowired
    private PlanCourseService planCourseService;

    @Autowired
    private PlanStaffService planStaffService;

    @Override
    public void pageQueryPlan(Page<Plan> pageParam, PlanQuery planQuery) {
        QueryWrapper<Plan> wrapper = new QueryWrapper<>();
        if (planQuery != null) {
            if (StringUtils.isNotEmpty(planQuery.getName())) {
                wrapper.like("name", planQuery.getName());
            }
            if (StringUtils.isNotEmpty(planQuery.getTeacherId())) {
                wrapper.eq("teacher_id", planQuery.getTeacherId());
            }
            if (StringUtils.isNotEmpty(planQuery.getBeginTime())) {
                wrapper.ge("begin_time", planQuery.getBeginTime());
            }
            if (StringUtils.isNotEmpty(planQuery.getEndTime())) {
                wrapper.le("end_time", planQuery.getEndTime());
            }
        }
        baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public PlanVo getPlanVoById(String planId) {
        PlanVo planVo = new PlanVo();
        // 查询主表数据
        Plan plan = baseMapper.selectById(planId);
        // 封装主表数据
        planVo.setName(plan.getName());
        planVo.setContent(plan.getContent());
        planVo.setPurpose(plan.getPurpose());
        planVo.setScore(plan.getScore());
        planVo.setTeacherId(plan.getTeacherId());
        List<Date> dates = new ArrayList<>();
        dates.add(plan.getBeginTime());
        dates.add(plan.getEndTime());
        planVo.setDate(dates);
        // 查询计划课程表数据
        QueryWrapper<PlanCourse> planCourseQueryWrapper = new QueryWrapper<>();
        planCourseQueryWrapper.eq("plan_id", planId);
        List<PlanCourse> list = planCourseService.list(planCourseQueryWrapper.select("course_id"));
        List<String> courses = new ArrayList<>();
        for (PlanCourse planCourse : list) {
            courses.add(planCourse.getCourseId());
        }
        // 查询计划职工表
        QueryWrapper<PlanStaff> planStaffQueryWrapper = new QueryWrapper<>();
        planStaffQueryWrapper.eq("plan_id", planId);
        List<PlanStaff> planStaffList = planStaffService.list(planStaffQueryWrapper.select("staff_id"));
        List<String> staffs = new ArrayList<>();
        for (PlanStaff planStaff : planStaffList) {
            staffs.add(planStaff.getStaffId());
        }
        // 设置课程和职工相关信息
        planVo.setCourses(courses);
        planVo.setStaffs(staffs);
        return planVo;
    }

    @Override
    public boolean addPlan(PlanVo planVo, String status) {
        Plan plan = new Plan();
        // 主表插入计划基本信息
        plan.setName(planVo.getName());
        plan.setContent(planVo.getContent());
        plan.setPurpose(planVo.getPurpose());
        plan.setTeacherId(planVo.getTeacherId());
        plan.setScore(planVo.getScore());
        plan.setBeginTime(planVo.getDate().get(0));
        plan.setEndTime(planVo.getDate().get(1));
        plan.setStatus(status);
        int planLine = baseMapper.insert(plan);

        // 计划课程表插入计划内课程相关信息，在计划基本信息表插入成功的前提下才能解析计划课程表进行插入操作
        if (planLine > 0) {
            // 可能包含多条课程，挨个解析，课程list存储格式 courseId + "-" + courseName
            for (String s : planVo.getCourses()) {
                PlanCourse planCourse = new PlanCourse();
                planCourse.setCourseId(s);
                planCourse.setPlanId(plan.getId());
                boolean planCourseSuccess = planCourseService.save(planCourse);
                if (!planCourseSuccess) {
                    throw new TrainingException(500, "计划课程表表插入失败！");
                }
            }

            // 计划职工表添加计划内职工相关信息，上面两个表插入成功才能进行第三步插入
            // 多个职工信息，遍历获取
            for (String s : planVo.getStaffs()) {
                PlanStaff planStaff = new PlanStaff();
                planStaff.setPlanId(plan.getId());
                planStaff.setStaffId(s);
                planStaff.setTeacherId(planVo.getTeacherId());
                boolean planStaffSuccess = planStaffService.save(planStaff);
                if (!planStaffSuccess) {
                    throw new TrainingException(500, "计划职工表插入失败！");
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updatePlan(String planId, PlanVo planVo, String status) {
        // 更新计划职工表信息
        List<String> staffs = planVo.getStaffs();
        // 先查询再更新
        QueryWrapper<PlanStaff> planStaffQueryWrapper = new QueryWrapper<>();
        planStaffQueryWrapper.eq("plan_id", planId);
        List<PlanStaff> planStaffList = planStaffService.list(planStaffQueryWrapper);
        List<String> oldStaffs = new ArrayList<>();
        for (PlanStaff planStaff : planStaffList) {
            // 项目负责人变更的话也需要及时更新
            if (!planStaff.getTeacherId().equals(planVo.getTeacherId())) {
                planStaff.setTeacherId(planVo.getTeacherId());
            }
            oldStaffs.add(planStaff.getStaffId());
            // 不在最新职工信息中的都删掉
            if (!staffs.contains(planStaff.getStaffId())) {
                planStaffService.removeById(planStaff.getId());
            }
        }
        // 对已有的更新
        if (planStaffList.size() != 0) {
            planStaffService.updateBatchById(planStaffList);
        }
        // 再次循环加入准备更新的list中
        for (String s : staffs) {
            // 不包括s，就要加入待更新队列中
            if (!oldStaffs.contains(s)) {
                PlanStaff planStaff = new PlanStaff();
                planStaff.setPlanId(planId);
                planStaff.setTeacherId(planVo.getTeacherId());
                planStaff.setStaffId(s);
                planStaffService.save(planStaff);
            }
        }
        // 更新计划课程表信息
        List<String> courses = planVo.getCourses();
        QueryWrapper<PlanCourse> planCourseQueryWrapper = new QueryWrapper<>();
        planCourseQueryWrapper.eq("plan_id", planId);
        List<PlanCourse> planCourseList = planCourseService.list(planCourseQueryWrapper);
        List<String> oldCourses = new ArrayList<>();
        for (PlanCourse planCourse : planCourseList) {
            oldCourses.add(planCourse.getCourseId());
            if (!courses.contains(planCourse.getCourseId())) {
                planCourseService.removeById(planCourse.getId());
            }
        }
        // 执行更新
        if (planCourseList.size() != 0) {
            planCourseService.updateBatchById(planCourseList);
        }
        for (String s : courses) {
            if (!oldCourses.contains(s)) {
                PlanCourse planCourse = new PlanCourse();
                planCourse.setPlanId(planId);
                planCourse.setCourseId(s);
                planCourseService.save(planCourse);
            }
        }

        // 更新主表信息
        Plan plan = new Plan();
        // 主表插入计划基本信息
        plan.setId(planId);
        plan.setName(planVo.getName());
        plan.setContent(planVo.getContent());
        plan.setPurpose(planVo.getPurpose());
        plan.setTeacherId(planVo.getTeacherId());
        plan.setScore(planVo.getScore());
        plan.setBeginTime(planVo.getDate().get(0));
        plan.setEndTime(planVo.getDate().get(1));
        plan.setStatus(status);
        int line = baseMapper.updateById(plan);
        return line > 0;
    }

    @Override
    public boolean deletePlan(String planId) {
        // 删除主表信息
        int line = baseMapper.deleteById(planId);
        if (line > 0) {
            return planCourseService.removeByPlanId(planId) && planStaffService.removeByPlanId(planId);
        } else {
            throw new TrainingException(500, "培训计划信息不存在，删除失败！");
        }
    }
}
