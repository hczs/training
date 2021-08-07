package com.hc.learning.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author houcheng
 * @since 2021-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="StaffCourse对象", description="")
public class StaffCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "职工课程学习情况id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "职工id")
    private String staffId;

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "是否完成 1（true）已完成， 0（false）未完成，默认0")
    private Integer accomplishFlag;

    @ApiModelProperty(value = "已学习小节数目，默认0")
    private Integer learningVideoNum;

    @ApiModelProperty(value = "课程小节总数")
    private Integer allVideoNum;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Integer delFlag;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "上次学习的小节id")
    private String lastVideoId;


}
