package com.hc.ucenter.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hc.common.utils.ResultStatus;
import com.hc.common.utils.TrainingResult;
import com.hc.ucenter.entity.Staff;
import com.hc.ucenter.entity.vo.PasswordVo;
import com.hc.ucenter.service.StaffService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TreeMap;

/**
 * @author ：hc
 * @date ：Created in 2021/3/18 20:21
 * @modified By：
 */
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/all")
    @ApiOperation("获取所有职工信息")
    public TrainingResult getAllStaff() {
        List<Staff> list = staffService.list(null);
        return TrainingResult.ok().data("staffList", list);
    }

    @PostMapping("/info")
    @ApiOperation("更新职工信息")
    public TrainingResult updateStaffInfo(@RequestBody Staff staff) {
        boolean success = staffService.updateById(staff);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，修改资料失败！");
    }

    @PostMapping("/pwd")
    @ApiOperation("更新密码信息")
    public TrainingResult updatePassword(@RequestBody PasswordVo passwordVo) {
        // 参数检查
        if (StringUtils.isEmpty(passwordVo.getId())) {
            return TrainingResult.error().status(ResultStatus.UNAUTHORIZED.getCode()).message("请先登录！");
        }
        if (StringUtils.isEmpty(passwordVo.getOldPassword())) {
            return TrainingResult.error().message("请输入原密码！");
        }
        if (StringUtils.isEmpty(passwordVo.getNewPassword())) {
            return TrainingResult.error().message("请输入新密码！");
        }
        // 更新
        boolean success = staffService.updatePassword(passwordVo);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，密码修改失败！");
    }
}
