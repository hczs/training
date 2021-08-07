package com.hc.learning.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.hc.learning.entity.StaffCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.learning.entity.vo.JoinCourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author houcheng
 * @since 2021-03-11
 */
public interface StaffCourseMapper extends BaseMapper<StaffCourse> {


    /**
     * 查询指定职工参与的课程
     * @param page 分页对象
     * @param staffId 职工id
     * @return JoinCourseVo集合
     */
    IPage<JoinCourseVo> getJoinCourses(IPage<JoinCourseVo> page, String staffId);
}
