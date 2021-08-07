package com.hc.learning.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.learning.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.learning.entity.vo.CoursePublishVo;
import com.hc.learning.entity.vo.CourseQuery;
import com.hc.learning.entity.vo.CourseVo;
import com.hc.learning.entity.vo.front.CourseInfoFrontVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houcheng
 * @since 2021-02-24
 */
public interface CourseService extends IService<Course> {

    /**
     * 分页查询（默认查询已暂存和已发布的课程）
     * @param pageParam 分页参数
     * @param courseQuery 课程查询vo
     */
    void pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

    /**
     * 获取热门课程，limit 4
     * @return 课程列表
     */
    List<Course> getHotCourses();

    /**
     * 前台条件分页查询（默认查询所有已发布课程）
     * @param page 分页查询对象
     * @param courseQuery 条件查询对象
     * @return 包含各种分页数据的map
     */
    Map<String, Object> courseFrontQuery(Page<Course> page, CourseQuery courseQuery);

    /**
     * 获取课程详情信息（前台）
     * @param courseId 课程id
     * @return CourseInfoFrontVo
     */
    CourseInfoFrontVo getCourseInfoFrontVoById(String courseId);

    /**
     * 添加课程基本信息
     * @param courseVo 课程vo对象
     * @return courseId
     */
    String addCourse(CourseVo courseVo);

    /**
     * 更新课程基本信息
     * @param courseVo 课程vo对象
     * @return 是否成功
     */
    boolean updateCourse(CourseVo courseVo);

    /**
     * 根据课程id更新评论数（评论数+1）
     * @param courseId 课程id
     * @return
     */
    boolean updateCommentNum(String courseId);

    /**
     * 参与课程
     * @param staffId 用户id
     * @param courseId 课程id
     * @return 是否成功
     */
    boolean joinCourse(String staffId, String courseId);

    /**
     * 获取课程发布信息
     * @param id 课程id
     * @return CoursePublishVo
     */
    CoursePublishVo getCoursePublishInfo(String id);

    /**
     * 发布课程
     * @param id 课程id
     * @return 是否成功
     */
    boolean publishCourse(String id);

    /**
     * 暂存待发布课程
     * @param id 课程id
     * @return 是否成功
     */
    boolean savePublishCourse(String id);

    /**
     * 删除课程信息
     * @param id 课程id
     * @return 是否成功
     */
    boolean deleteCourse(String id);
}
