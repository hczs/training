package com.hc.plan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hc.plan.entity.PlanStaff;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.plan.entity.vo.PlanStaffQuery;
import com.hc.plan.entity.vo.PlanStaffVo;
import com.hc.plan.entity.vo.front.PlanFrontVo;
import com.hc.plan.entity.vo.front.PlanSummaryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houcheng
 * @since 2021-03-26
 */
public interface PlanStaffService extends IService<PlanStaff> {

    /**
     * 根据计划id删除相应计划职工表中的数据
     * @param planId 计划id
     * @return 是否成功
     */
    boolean removeByPlanId(String planId);

    /**
     * 分页查询指定职工参与的培训计划
     * @param page 分页参数
     * @param staffId 职工id
     * @return 分页对象
     */
    IPage<PlanFrontVo> getPlanInfoByStaffId(IPage<PlanFrontVo> page, String staffId);

    /**
     * 添加职工总结
     * @param staffId 职工id
     * @param planSummaryVo planSummaryVo
     * @return 是否成功
     */
    boolean addSummary(String staffId, PlanSummaryVo planSummaryVo);

    /**
     * 分页查询职工培训计划参与情况
     * @param page 分页对象
     * @param planStaffQuery 条件
     * @return 分页对象
     */
    IPage<PlanStaffVo> getPlanListPage(IPage<PlanStaffVo> page, PlanStaffQuery planStaffQuery);

    /**
     * 根据planStaff表id更新职工计划分数
     * @param planStaffVo planStaffVo
     * @return 是否成功
     */
    boolean updateStaffScore(PlanStaffVo planStaffVo);
}
