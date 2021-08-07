package com.hc.ucenter.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.hc.common.utils.JwtUtils;
import com.hc.common.utils.TrainingResult;
import com.hc.ucenter.entity.Admin;
import com.hc.ucenter.entity.vo.AdminVo;
import com.hc.ucenter.entity.vo.LoginVo;
import com.hc.ucenter.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.TreeMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-04-01
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    @ApiOperation("管理员后台登录")
    public TrainingResult adminLogin(@RequestBody LoginVo loginVo) {
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return TrainingResult.error();
        }
        String token = adminService.login(username, password);
        return TrainingResult.ok().data("token", token);
    }

    @GetMapping("/info")
    @ApiOperation("管理员登录后获取信息（根据token）")
    public TrainingResult getInfo(HttpServletRequest request) {
        String adminId = JwtUtils.getStaffIdByJwtToken(request);
        Admin adminInfo = adminService.getInfo(adminId);
        return TrainingResult.ok().data("avatar", adminInfo.getAvatar()).data("name", adminInfo.getUsername()).data("roles", "admin");
    }

    @PostMapping("/add")
    @ApiOperation("添加管理员")
    public TrainingResult addAdmin(@RequestBody AdminVo adminVo) {
        boolean success = adminService.addAdmin(adminVo);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，添加失败！");
    }

}

