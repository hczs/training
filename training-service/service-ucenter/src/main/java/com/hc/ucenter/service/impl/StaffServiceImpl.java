package com.hc.ucenter.service.impl;

import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.common.exception.TrainingException;
import com.hc.common.utils.JwtUtils;
import com.hc.ucenter.entity.Staff;
import com.hc.ucenter.entity.vo.PasswordVo;
import com.hc.ucenter.entity.vo.RegisterVo;
import com.hc.ucenter.mapper.StaffMapper;
import com.hc.ucenter.service.StaffService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houcheng
 * @since 2021-03-05
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements StaffService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String sendCode(String mail) {
        System.out.println("目标邮件地址：" + mail);
        String code = generateCode();
        // 设置标题
        String subject = "欢迎注册职工业务培训网站！";
        // 设置正文
        String content = "尊敬的用户您好，您正在注册职工业务培训网站，您的验证码为" + code + "！" +
                "验证码五分钟内有效，请确保是本人操作，不要将验证码泄露给其他人!";
        try {
            System.out.println("mail参数" + mail + subject + content);
            MailUtil.send(mail, subject, content, false);
            System.out.println("发送完成！" + "目标邮件地址：" + mail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TrainingException(500, "请检查邮箱格式是否正确！");
        }
        return code;
    }

    @Override
    public boolean register(RegisterVo registerVo) {
        String mail = registerVo.getMail();
        String password = registerVo.getPassword();
        String username = registerVo.getUsername();
        String code = registerVo.getCode();

        if (StringUtils.isEmpty(mail) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(username) || StringUtils.isEmpty(code)) {
            throw new TrainingException(500, "注册失败，请填写完整注册信息！");
        }

        // 检查邮箱是否重复
        QueryWrapper<Staff> wrapper = new QueryWrapper<>();
        wrapper.eq("mail", mail);
        Integer count = baseMapper.selectCount(wrapper);
        if (count >= 1) {
            throw new TrainingException(500, "注册失败，邮箱重复！");
        }

        // 检查昵称是否重复
        QueryWrapper<Staff> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("username", username);
        Integer count1 = baseMapper.selectCount(wrapper1);
        if (count1 >= 1) {
            throw new TrainingException(500, "注册失败，用户名重复！");
        }

        // 检查验证码
        String redisCode = redisTemplate.opsForValue().get(mail);
        if (!code.equals(redisCode)) {
            throw new TrainingException(500, "注册失败，验证码错误！");
        }

        // 其他情况正常注册
        Staff staff = new Staff();
        staff.setMail(mail);
        staff.setUsername(username);
        staff.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        staff.setAvatar("http://hsunnyc.oss-cn-beijing.aliyuncs.com/avatar/1.jpg");

        int line = baseMapper.insert(staff);
        return line == 1;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        QueryWrapper<Staff> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Staff staff = baseMapper.selectOne(wrapper);
        // 是否可以成功查询到数据
        if (null == staff) {
            throw new TrainingException(500, "用户名或密码错误！");
        }
        // 校验密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(staff.getPassword())) {
            throw new TrainingException(500, "用户名或密码错误！");
        }
        // 通过重重检测，可以给token了
        Map<String, Object> map = new HashMap<>();
        if (staff.getPermission() == 1) {
            map.put("token", JwtUtils.getJwtToken(staff.getId(), staff.getUsername()));
        } else {
            map.put("userToken", JwtUtils.getJwtToken(staff.getId(), staff.getUsername()));
        }
        return map;
    }

    @Override
    public Staff getInfo(String staffId) {
        return baseMapper.selectById(staffId);
    }

    @Override
    public boolean updatePassword(PasswordVo passwordVo) {
        // 查出原来的密码
        Staff staff = baseMapper.selectById(passwordVo.getId());
        // 旧密码输入错误的话就返回错误
        if (!staff.getPassword().equals(DigestUtils.md5DigestAsHex(passwordVo.getOldPassword().getBytes()))) {
            throw new TrainingException(500, "原密码输入错误");
        }
        // 通过执行更新
        staff.setPassword(DigestUtils.md5DigestAsHex(passwordVo.getNewPassword().getBytes()));
        int line = baseMapper.updateById(staff);
        return line > 0;
    }

    /**
     * 生成四位数验证码
     * @return 四位数验证码
     */
    private String generateCode() {
        String source = "1234567890";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i=0; i<4; i++) {
            int index = random.nextInt(source.length());
            // 直接charAt方法也是O(1)，因为String底层是维护着一个final的value数组
            result.append(source.charAt(index));
        }
        return result.toString();
    }
}
