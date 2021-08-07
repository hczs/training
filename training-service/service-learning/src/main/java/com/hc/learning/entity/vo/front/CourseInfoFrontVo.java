package com.hc.learning.entity.vo.front;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：hc
 * @date ：Created in 2021/3/5 14:37
 * @modified By：
 */
@Data
public class CourseInfoFrontVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程封面url")
    private String cover;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "课时数")
    private Integer lessonNum;

    @ApiModelProperty(value = "浏览量")
    private Integer viewCount;

    @ApiModelProperty(value = "参与学习人数")
    private Integer learningNum;

    @ApiModelProperty(value = "课程简介")
    private String description;

    @ApiModelProperty(value = "一级分类名字")
    private String categoryLevelOne;

    @ApiModelProperty(value = "二级分类名字")
    private String categoryLevelTwo;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "讲师介绍")
    private String introduce;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;
}
