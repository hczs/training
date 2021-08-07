package com.hc.learning.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程发布信息vo
 * @author ：hc
 * @date ：Created in 2021/3/1 12:00
 */
@Data
public class CoursePublishVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程封面url")
    private String cover;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "课时数")
    private Integer lessonNumber;

    @ApiModelProperty(value = "课程简介")
    private String description;

    @ApiModelProperty(value = "一级分类名字")
    private String categoryOne;

    @ApiModelProperty(value = "二级分类名字")
    private String categoryTwo;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;
}
