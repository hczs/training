package com.hc.plan.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：hc
 * @date ：Created in 2021/3/28 15:40
 * @modified By：
 */
@Data
public class PlanStaffVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "计划名称")
    private String name;

    @ApiModelProperty(value = "职工id")
    private String staffId;

    @ApiModelProperty(value = "负责人（讲师）id")
    private String teacherId;

    @ApiModelProperty(value = "计划总分数")
    private Integer score;

    @ApiModelProperty(value = "职工所得分数")
    private Integer staffScore;

    @ApiModelProperty(value = "职工总结")
    private String summary;

    @ApiModelProperty(value = "计划开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginTime;

    @ApiModelProperty(value = "计划结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;
}
