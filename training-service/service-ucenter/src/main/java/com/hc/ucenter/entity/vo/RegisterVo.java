package com.hc.ucenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：hc
 * @date ：Created in 2021/3/5 21:13
 */
@Data
public class RegisterVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String mail;

    @ApiModelProperty(value = "验证码")
    private String code;
}
