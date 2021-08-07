package com.hc.plan.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.common.utils.JwtUtils;
import com.hc.common.utils.ResultStatus;
import com.hc.common.utils.TrainingResult;
import com.hc.plan.entity.vo.PlanStaffQuery;
import com.hc.plan.entity.vo.PlanStaffVo;
import com.hc.plan.entity.vo.front.PlanFrontVo;
import com.hc.plan.entity.vo.front.PlanSummaryVo;
import com.hc.plan.service.PlanStaffService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/plan-staff")
public class PlanStaffController {

    @Autowired
    private PlanStaffService planStaffService;

    @GetMapping("/{page}/{limit}")
    @ApiOperation("查询某个用户参与的培训计划")
    public TrainingResult getFrontPlanInfo(@PathVariable("page") Long page,
                                           @PathVariable("limit") Long limit,
                                           HttpServletRequest request) {
        // 检查是否登录
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.ok().status(ResultStatus.UNAUTHORIZED.getCode()).message("请先登录！");
        } else {
            // 查询用户参与的培训计划
            Page<PlanFrontVo> voPage = new Page<>(page, limit);
            IPage<PlanFrontVo> pageResult = planStaffService.getPlanInfoByStaffId(voPage, staffId);
            return TrainingResult.ok().data("total", pageResult.getTotal()).data("rows", pageResult.getRecords());
        }
    }

    @PostMapping("/addSummary")
    @ApiOperation("计划总结")
    public TrainingResult addSummary(@RequestBody PlanSummaryVo planSummaryVo, HttpServletRequest request) {
        // 检查是否登录
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.ok().status(ResultStatus.UNAUTHORIZED.getCode()).message("请先登录！");
        } else {
            boolean success = planStaffService.addSummary(staffId, planSummaryVo);
            return success ? TrainingResult.ok() : TrainingResult.error();
        }
    }

    @PostMapping("/all/{page}/{limit}")
    @ApiOperation("查询所有职工参与的培训计划")
    public TrainingResult getAllPlan(@PathVariable("page") Long page,
                                     @PathVariable("limit") Long limit,
                                     @RequestBody(required = false) PlanStaffQuery planStaffQuery) {
        Page<PlanStaffVo> planStaffVoPage = new Page<>(page, limit);
        IPage<PlanStaffVo> planListPage = planStaffService.getPlanListPage(planStaffVoPage, planStaffQuery);
        return TrainingResult.ok().data("total", planListPage.getTotal()).data("rows", planListPage.getRecords());
    }

    @PostMapping("/score")
    @ApiOperation("根据主键id设置职工计划得分")
    public TrainingResult updateScore(@RequestBody PlanStaffVo planStaffVo) {
        boolean success = planStaffService.updateStaffScore(planStaffVo);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误！评分失败！");
    }

}

