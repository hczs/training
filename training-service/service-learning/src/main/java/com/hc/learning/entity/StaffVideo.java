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
@ApiModel(value="StaffVideo对象", description="")
public class StaffVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "职工小节表id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "职工id")
    private String staffId;

    @ApiModelProperty(value = "小节id")
    private String videoId;

    @ApiModelProperty(value = "已学习时长（s）")
    private Float learningTime;

    @ApiModelProperty(value = "小节总时长（s）")
    private Float duration;

    @ApiModelProperty(value = "上次观看视频时长记录（s）")
    private Float lastTime;

    @ApiModelProperty(value = "小节是否学习完成，1（true）已完成， 0（false）未完成，默认0")
    private Integer accomplishFlag;

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


}
