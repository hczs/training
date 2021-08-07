package com.hc.plan.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.hc.plan.entity.PlanStaff;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.plan.entity.vo.PlanStaffVo;
import com.hc.plan.entity.vo.front.PlanFrontVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author houcheng
 * @since 2021-03-26
 */
public interface PlanStaffMapper extends BaseMapper<PlanStaff> {

    /**
     * 分页查询某个职工所参与的培训计划
     * @param page 分页参数
     * @param staffId 职工id
     * @return 分页对象
     */
    IPage<PlanFrontVo> getPlanFrontVoPage(IPage<PlanFrontVo> page, String staffId);

    /**
     * 分页查询所有职工参与的培训计划
     * @param page 分页参数
     * @param queryWrapper wrapper
     * @return 分页对象
     */
    IPage<PlanStaffVo> getPlanListPage(IPage<PlanStaffVo> page, @Param(Constants.WRAPPER) Wrapper<PlanStaffVo> queryWrapper);

}
