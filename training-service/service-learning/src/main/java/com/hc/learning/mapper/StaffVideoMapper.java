package com.hc.learning.mapper;

import com.hc.learning.entity.StaffVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author houcheng
 * @since 2021-03-11
 */
public interface StaffVideoMapper extends BaseMapper<StaffVideo> {

    /**
     * 获取指定员工的学习时长
     * @param staffId 员工id
     * @return Integer 的学习时长
     */
    Integer getLearning(String staffId);

}
