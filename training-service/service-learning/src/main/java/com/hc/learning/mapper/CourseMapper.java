package com.hc.learning.mapper;

import com.hc.learning.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.learning.entity.vo.CoursePublishVo;
import com.hc.learning.entity.vo.front.CourseInfoFrontVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author houcheng
 * @since 2021-02-24
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 获取课程发布信息
     * @param id 课程id
     * @return CoursePublishVo
     */
    CoursePublishVo getCoursePublishById(String id);

    /**
     * 获取课程信息（前台）
     * @param id 课程id
     * @return CourseInfoFrontVo
     */
    CourseInfoFrontVo getCourseInfoFrontById(String id);

    /**
     * 获取最热门的四门课程展示在首页，按照学习人数，课程浏览量两个参数来决定热门程度
     * @return 课程列表
     */
    List<Course> getHotCourse();
}
