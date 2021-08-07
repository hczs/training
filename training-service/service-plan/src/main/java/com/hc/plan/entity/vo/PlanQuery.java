package com.hc.plan.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：hc
 * @date ：Created in 2021/3/26 16:58
 * @modified By：
 */
@Data
public class PlanQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "计划名称")
    private String name;

    @ApiModelProperty(value = "负责人（讲师）id")
    private String teacherId;

    @ApiModelProperty(value = "计划开始时间")
    private String beginTime;

    @ApiModelProperty(value = "计划结束时间")
    private String endTime;
}
