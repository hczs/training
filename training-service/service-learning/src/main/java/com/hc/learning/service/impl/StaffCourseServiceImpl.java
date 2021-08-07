package com.hc.learning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.learning.entity.StaffCourse;
import com.hc.learning.entity.vo.JoinCourseVo;
import com.hc.learning.mapper.StaffCourseMapper;
import com.hc.learning.service.CourseService;
import com.hc.learning.service.StaffCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houcheng
 * @since 2021-03-11
 */
@Service
public class StaffCourseServiceImpl extends ServiceImpl<StaffCourseMapper, StaffCourse> implements StaffCourseService {

    @Autowired
    private CourseService courseService;

    @Override
    public boolean isStudyCourse(String staffId, String courseId) {
        QueryWrapper<StaffCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);
        wrapper.eq("course_id", courseId);
        // 查count，能查到记录就行，不在乎查出来记录是啥情况
        Integer result = baseMapper.selectCount(wrapper);
        return result > 0;
    }

    @Override
    public IPage<JoinCourseVo> getJoinCourse(Page<JoinCourseVo> voPage, String staffId) {
        return baseMapper.getJoinCourses(voPage, staffId);
    }

    @Override
    public Double getPercentage(String staffId, String courseId) {
        QueryWrapper<StaffCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);
        wrapper.eq("course_id", courseId);
        StaffCourse staffCourse = baseMapper.selectOne(wrapper);
        return (staffCourse.getLearningVideoNum() * 1.0 / staffCourse.getAllVideoNum());
    }

    @Override
    public boolean updateLearningVideoNum(String courseId, String staffId) {
        QueryWrapper<StaffCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("staff_id", staffId);
        StaffCourse staffCourse = baseMapper.selectOne(wrapper);
        // 已学习小节数+1
        staffCourse.setLearningVideoNum(staffCourse.getLearningVideoNum() + 1);
        // 如果已学习小节和总共的小节数相同，就标记已经完成此课程
        if (staffCourse.getLearningVideoNum().equals(staffCourse.getAllVideoNum())) {
            staffCourse.setAccomplishFlag(1);
        }
        return baseMapper.updateById(staffCourse) > 0;
    }
}
