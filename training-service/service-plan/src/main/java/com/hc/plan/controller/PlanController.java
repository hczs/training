package com.hc.plan.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.common.utils.TrainingResult;
import com.hc.plan.entity.Plan;
import com.hc.plan.entity.vo.PlanQuery;
import com.hc.plan.entity.vo.PlanVo;
import com.hc.plan.service.PlanService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class PlanController {

    @Autowired
    private PlanService planService;

    @PostMapping("/{page}/{limit}")
    @ApiOperation("条件分页查询培训计划")
    public TrainingResult pageQueryPlan(@PathVariable(value = "page") Long page,
                                        @PathVariable(value = "limit") Long limit,
                                        @RequestBody(required = false) PlanQuery planQuery) {
        Page<Plan> planPage = new Page<>(page,limit);
        planService.pageQueryPlan(planPage, planQuery);
        return TrainingResult.ok().data("total", planPage.getTotal()).data("rows", planPage.getRecords());
    }

    @PostMapping("/add")
    @ApiOperation("添加计划")
    public TrainingResult addPlan(@RequestBody PlanVo planVo) {
        boolean success = planService.addPlan(planVo, "Normal");
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，计划发布失败！");
    }

    @PostMapping("/save")
    @ApiOperation("暂存计划")
    public TrainingResult savePlan(@RequestBody PlanVo planVo) {
        boolean success = planService.addPlan(planVo, "Draft");
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，计划发布失败！");
    }

    @PostMapping("/save/{id}")
    @ApiOperation("根据id更新并暂存计划信息")
    public TrainingResult updateSavePlan(@PathVariable("id") String planId,
                                         @RequestBody PlanVo planVo) {
        boolean success = planService.updatePlan(planId, planVo, "Draft");
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，计划更新失败！");
    }

    @PostMapping("/add/{id}")
    @ApiOperation("根据id更新并发布计划信息")
    public TrainingResult updateAddPlan(@PathVariable("id") String planId,
                                        @RequestBody PlanVo planVo) {
        boolean success = planService.updatePlan(planId, planVo, "Normal");
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，计划更新失败！");

    }

    @GetMapping("/{id}")
    @ApiOperation("根据id回显计划信息")
    public TrainingResult getPlanInfo(@PathVariable("id") String planId) {
        PlanVo planVo = planService.getPlanVoById(planId);
        return TrainingResult.ok().data("plan", planVo);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据id删除对应计划信息")
    public TrainingResult deletePlan(@PathVariable("id") String planId) {
        boolean success = planService.deletePlan(planId);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，计划删除失败！");
    }



    @GetMapping("/all")
    @ApiOperation("获取所有计划数据")
    public TrainingResult getAllPlan() {
        List<Plan> list = planService.list(null);
        return TrainingResult.ok().data("planList", list);
    }

}

