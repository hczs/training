package com.hc.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.common.exception.TrainingException;
import com.hc.common.utils.JwtUtils;
import com.hc.ucenter.entity.Admin;
import com.hc.ucenter.entity.Staff;
import com.hc.ucenter.entity.vo.AdminVo;
import com.hc.ucenter.mapper.AdminMapper;
import com.hc.ucenter.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houcheng
 * @since 2021-04-01
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public String login(String username, String password) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Admin admin = baseMapper.selectOne(wrapper);
        // 是否可以成功查询到数据
        if (null == admin) {
            throw new TrainingException(500, "用户名或密码错误！");
        }
        // 校验密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(admin.getPassword())) {
            throw new TrainingException(500, "用户名或密码错误！");
        }
        // 通过重重检测，可以给token了
        return JwtUtils.getJwtToken(admin.getId(), admin.getUsername());
    }

    @Override
    public Admin getInfo(String adminId) {
        return baseMapper.selectById(adminId);
    }

    @Override
    public boolean addAdmin(AdminVo adminVo) {
        Admin admin = new Admin();
        admin.setAvatar(adminVo.getAvatar());
        admin.setUsername(adminVo.getUsername());
        // 密码加密
        admin.setPassword(DigestUtils.md5DigestAsHex(adminVo.getPassword().getBytes()));
        int insert = baseMapper.insert(admin);
        return insert > 0;
    }
}
