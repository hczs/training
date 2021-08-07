package com.hc.ucenter.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @author ：hc
 * @date ：Created in 2021/4/4 21:32
 * @modified By：
 */
@Data
public class PasswordVo {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "原密码")
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    private String newPassword;
}
