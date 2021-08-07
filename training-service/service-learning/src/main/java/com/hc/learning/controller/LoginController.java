package com.hc.learning.controller;

import com.hc.common.utils.TrainingResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：hc
 * @date ：Created in 2021/2/21 21:30
 * @modified By：
 */
@RestController
public class LoginController {

    @PostMapping("/user/login")
    @ApiOperation("临时处理后台登录")
    public TrainingResult login() {
        return TrainingResult.ok().data("token","admin");
    }

    @GetMapping("/user/info")
    @ApiOperation("临时处理获取信息")
    public TrainingResult info() {
        return TrainingResult.ok()
                .data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "http://hsunnyc.oss-cn-beijing.aliyuncs.com/avatar/1.jpg");
    }

}
