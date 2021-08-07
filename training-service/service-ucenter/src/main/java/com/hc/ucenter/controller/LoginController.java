package com.hc.ucenter.controller;


import com.hc.common.utils.JwtUtils;
import com.hc.common.utils.ResultStatus;
import com.hc.common.utils.TrainingResult;
import com.hc.ucenter.entity.Staff;
import com.hc.ucenter.entity.vo.LoginVo;
import com.hc.ucenter.entity.vo.RegisterVo;
import com.hc.ucenter.service.StaffService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-03-05
 */
@RestController
public class LoginController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("send/{mail}")
    @ApiOperation("往指定邮箱发送验证码")
    public TrainingResult sendCode(@PathVariable(value = "mail") String mail) {
        // 先判空
        if (StringUtils.isEmpty(mail)) {
            return TrainingResult.error().message("邮箱不能为空");
        }
        // 查看是否获取过验证码了
        String code = redisTemplate.opsForValue().get(mail);
        if (StringUtils.isNotEmpty(code)) {
            return TrainingResult.error().message("已经发送过验证码了，请稍后获取");
        }
        // 发送验证码
        code = staffService.sendCode(mail);
        // 如果发送成功的话，就存入redis，并且设置过期时间五分钟
        redisTemplate.opsForValue().set(mail, code, 5, TimeUnit.MINUTES);
        // 返回成功信息
        return TrainingResult.ok();
    }

    @GetMapping("test/{mail}")
    public TrainingResult hello(@PathVariable(value = "mail") String mail) {
        String code = staffService.sendCode(mail);
        return TrainingResult.error().message(code);
    }

    @PostMapping("register")
    @ApiOperation("用户注册")
    public TrainingResult register(@RequestBody RegisterVo member) {
        if (member == null) {
            return TrainingResult.error().message("请输入数据！");
        }
        boolean result = staffService.register(member);
        if (result) {
            // 注册成功后自动登录
            Map<String, Object> map = staffService.login(member.getUsername(), member.getPassword());
            return TrainingResult.ok().message("注册成功！").data(map);
        }
        return TrainingResult.error();
    }

    @PostMapping("login")
    @ApiOperation("用户登录")
    public TrainingResult login(@RequestBody LoginVo loginVo) {
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return TrainingResult.error().message("用户名或密码为空！");
        }
        Map<String, Object> map = staffService.login(username, password);
        return TrainingResult.ok().data(map);
    }

    @GetMapping("info")
    @ApiOperation("根据请求头中的token信息获取用户信息")
    public TrainingResult getInfo(HttpServletRequest request) {
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.error().status(ResultStatus.UNAUTHORIZED.getCode()).message("请先登录！");
        }
        Staff staff = staffService.getInfo(staffId);
        staff.setPassword(null);
        if (staff.getPermission() == 1) {
            return TrainingResult.ok().data("avatar", staff.getAvatar()).data("name", staff.getUsername()).data("roles", "admin");
        } else {
            return TrainingResult.ok().data("staffInfo", staff);
        }
    }

    @GetMapping("info/{staffId}")
    @ApiOperation("根据用户id获取用户信息")
    public TrainingResult getUserInfo(@PathVariable(value = "staffId") String staffId) {
        Staff info = staffService.getInfo(staffId);
        // 返回给前台时清空密码等一些不必要的信息
        info.setPassword(null);
        return TrainingResult.ok().data("staffInfo", info);
    }
}

