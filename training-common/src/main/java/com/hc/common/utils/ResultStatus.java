package com.hc.common.utils;


import lombok.Getter;

/**
 * 状态码枚举类
 * @author hczs8
 */
@Getter
public enum ResultStatus {

    /**
     * 成功
     */
    SUCCESS(200),

    /**
     * 失败
     */
    ERROR(500),

    /**
     * 未授权（未登录）
     */
    UNAUTHORIZED(401);

    private Integer code;

    ResultStatus(Integer code) {
        this.code = code;
    }
}
