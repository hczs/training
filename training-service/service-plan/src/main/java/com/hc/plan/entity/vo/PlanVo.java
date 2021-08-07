package com.hc.plan.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：hc
 * @date ：Created in 2021/3/26 13:18
 * @modified By：
 */
@Data
@ToString
@ApiModel(value="PlanVo", description="")
public class PlanVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "计划名称")
    private String name;

    @ApiModelProperty(value = "计划内容")
    private String content;

    @ApiModelProperty(value = "计划目的")
    private String purpose;

    @ApiModelProperty(value = "负责人（讲师）id")
    private String teacherId;

    @ApiModelProperty(value = "计划总分数")
    private Integer score;

    @ApiModelProperty(value = "计划日期范围")
    private List<Date> date;

    @ApiModelProperty(value = "课程列表")
    private List<String> courses;

    @ApiModelProperty(value = "职工列表")
    private List<String> staffs;
}
