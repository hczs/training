package com.hc.learning.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author ：hc
 * @date ：Created in 2021/3/1 16:10
 * @modified By：
 */
@Data
public class CourseQuery {

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "课程分类id")
    private String categoryId;

    @ApiModelProperty(value = "课程分类父id")
    private String categoryParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程状态，Draft已保存未发布，provisional未保存临时数据，Normal已发布")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
