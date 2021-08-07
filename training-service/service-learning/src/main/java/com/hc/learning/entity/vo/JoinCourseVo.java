package com.hc.learning.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author     ：hc
 * @date       ：Created in 2021/4/3 12:58
 * @modified By：
 */
@Data
public class JoinCourseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "是否完成 1（true）已完成， 0（false）未完成，默认0")
    private Integer accomplishFlag;

    @ApiModelProperty(value = "已学习小节数目，默认0")
    private Integer learningVideoNum;

    @ApiModelProperty(value = "课程小节总数")
    private Integer allVideoNum;
}
