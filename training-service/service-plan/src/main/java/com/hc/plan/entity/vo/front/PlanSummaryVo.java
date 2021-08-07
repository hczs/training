package com.hc.plan.entity.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ：hc
 * @date ：Created in 2021/3/28 11:05
 */
@Data
@ToString
@ApiModel(value="PlanSummaryVo", description="")
public class PlanSummaryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "计划id")
    private String id;

    @ApiModelProperty(value = "职工总结")
    private String summary;
}
