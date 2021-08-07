package com.hc.plan.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：hc
 * @date ：Created in 2021/3/28 15:41
 * @modified By：
 */
@Data
public class PlanStaffQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "计划名称")
    private String name;

    @ApiModelProperty(value = "职工id")
    private String staffId;

    @ApiModelProperty(value = "负责人（讲师）id")
    private String teacherId;
}
