package com.hc.plan.entity.vo.front;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hc.plan.entity.Course;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：hc
 * @date ：Created in 2021/3/27 18:25
 * @modified By：
 */
@Data
@ToString
@ApiModel(value="PlanFrontVo", description="")
public class PlanFrontVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "计划id")
    private String id;

    @ApiModelProperty(value = "计划名称")
    private String name;

    @ApiModelProperty(value = "计划内容")
    private String content;

    @ApiModelProperty(value = "计划目的")
    private String purpose;

    @ApiModelProperty(value = "负责人（讲师）名称")
    private String teacherName;

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
