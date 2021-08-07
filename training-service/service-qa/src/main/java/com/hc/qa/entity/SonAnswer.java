package com.hc.qa.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：hc
 * @date ：Created in 2021/4/2 21:42
 */
@Data
public class SonAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "回答内容")
    private String content;

    @ApiModelProperty(value = "回答人id")
    private String staffId;

    @ApiModelProperty(value = "回答人头像")
    private String avatar;

    @ApiModelProperty(value = "回答人用户名")
    private String username;

    @ApiModelProperty(value = "回复人用户名")
    private String replyName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
