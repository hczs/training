package com.hc.ucenter.service;

import com.hc.ucenter.entity.Staff;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.ucenter.entity.vo.PasswordVo;
import com.hc.ucenter.entity.vo.RegisterVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houcheng
 * @since 2021-03-05
 */
public interface StaffService extends IService<Staff> {

    /**
     * 向目标邮箱发送验证码
     * @param mail 目标邮箱
     * @return 成功返回验证码，不成功抛出异常
     */
    String sendCode(String mail);

    /**
     * 注册方法
     * @param registerVo 前端页面封装会员对象
     * @return 是否成功
     */
    boolean register(RegisterVo registerVo);

    /**
     * 登录处理
     * @param username 昵称
     * @param password 密码
     * @return 成功返回token
     */
    Map<String, Object> login(String username, String password);

    /**
     * 根据用户id获取用户信息
     * @param staffId 用户id
     * @return 用户信息，Staff
     */
    Staff getInfo(String staffId);

    /**
     * 更新密码
     * @param staffId 职工id
     * @param passwordVo passwordVo
     * @return 是否成功
     */
    boolean updatePassword(PasswordVo passwordVo);

}
