package com.hc.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hc.plan.entity.PlanStaff;
import com.hc.plan.entity.vo.PlanStaffQuery;
import com.hc.plan.entity.vo.PlanStaffVo;
import com.hc.plan.entity.vo.front.PlanFrontVo;
import com.hc.plan.entity.vo.front.PlanSummaryVo;
import com.hc.plan.mapper.PlanStaffMapper;
import com.hc.plan.service.PlanStaffService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houcheng
 * @since 2021-03-26
 */
@Service
public class PlanStaffServiceImpl extends ServiceImpl<PlanStaffMapper, PlanStaff> implements PlanStaffService {

    @Override
    public boolean removeByPlanId(String planId) {
        QueryWrapper<PlanStaff> wrapper = new QueryWrapper<>();
        wrapper.eq("plan_id", planId);
        baseMapper.delete(wrapper);
        return true;
    }

    @Override
    public IPage<PlanFrontVo> getPlanInfoByStaffId(IPage<PlanFrontVo> page, String staffId) {
        return baseMapper.getPlanFrontVoPage(page, staffId);
    }

    @Override
    public boolean addSummary(String staffId, PlanSummaryVo planSummaryVo) {
        QueryWrapper<PlanStaff> wrapper = new QueryWrapper<>();
        wrapper.eq("plan_id", planSummaryVo.getId());
        PlanStaff planStaff = new PlanStaff();
        planStaff.setStaffSummary(planSummaryVo.getSummary());
        int line = baseMapper.update(planStaff, wrapper);
        return line > 0;
    }

    @Override
    public IPage<PlanStaffVo> getPlanListPage(IPage<PlanStaffVo> page, PlanStaffQuery planStaffQuery) {
        QueryWrapper<PlanStaffVo> wrapper = new QueryWrapper<>();
        wrapper.eq("p.del_flag", 0);
        wrapper.eq("ps.del_flag", 0);
        if (StringUtils.isNotEmpty(planStaffQuery.getName())) {
            wrapper.like("p.name", planStaffQuery.getName());
        }
        if (StringUtils.isNotEmpty(planStaffQuery.getStaffId())) {
            wrapper.eq("ps.staff_id", planStaffQuery.getStaffId());
        }
        if (StringUtils.isNotEmpty(planStaffQuery.getTeacherId())) {
            wrapper.eq("ps.teacher_id", planStaffQuery.getTeacherId());
        }
        return baseMapper.getPlanListPage(page, wrapper);
    }

    @Override
    public boolean updateStaffScore(PlanStaffVo planStaffVo) {
        PlanStaff planStaff = new PlanStaff();
        planStaff.setId(planStaffVo.getId());
        planStaff.setStaffScore(planStaffVo.getStaffScore());
        return baseMapper.updateById(planStaff) > 0;
    }
}
