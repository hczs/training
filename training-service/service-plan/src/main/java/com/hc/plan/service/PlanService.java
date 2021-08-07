package com.hc.plan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.plan.entity.Plan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.plan.entity.vo.PlanQuery;
import com.hc.plan.entity.vo.PlanVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houcheng
 * @since 2021-03-26
 */
public interface PlanService extends IService<Plan> {


    /**
     * 条件分页查询培训计划数据
     * @param pageParam 分页对象
     * @param planQuery 条件查询vo
     */
    void pageQueryPlan(Page<Plan> pageParam, PlanQuery planQuery);

    /**
     * 根据计划id回显计划信息
     * @param planId 计划id
     * @return 计划信息PlanVo
     */
    PlanVo getPlanVoById(String planId);

    /**
     * 插入计划信息（同步添加计划课程表，计划职工表相关信息）
     * @param planVo planVo
     * @param status 计划状态
     * @return 是否成功
     */
    boolean addPlan(PlanVo planVo, String status);

    /**
     * 根据id更新计划信息（包括计划课程表，计划职工表）
     * @param planId 计划id
     * @param planVo planVo
     * @param status 计划状态
     * @return 是否成功
     */
    boolean updatePlan(String planId, PlanVo planVo, String status);

    /**
     * 删除计划信息（将同步删除计划课程表和计划职工表相关信息）
     * @param planId 计划id
     * @return 是否成功
     */
    boolean deletePlan(String planId);

}
