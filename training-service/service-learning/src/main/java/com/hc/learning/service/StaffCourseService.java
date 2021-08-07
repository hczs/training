package com.hc.learning.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.learning.entity.StaffCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.learning.entity.vo.JoinCourseVo;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houcheng
 * @since 2021-03-11
 */
public interface StaffCourseService extends IService<StaffCourse> {


    /**
     * 查询指定用户是否学习了指定课程
     * @param staffId 用户id
     * @param courseId 课程id
     * @return 是否学习
     */
    boolean isStudyCourse(String staffId, String courseId);

    /**
     * 查询指定用户参与的课程
     * @param staffId 用户
     * @return 分页对象
     */
    IPage<JoinCourseVo> getJoinCourse(Page<JoinCourseVo> voPage, String staffId);

    /**
     * 获取指定用户指定课程的模仿进度
     * @param staffId 用户id
     * @param courseId 课程id
     * @return 播放进度
     */
    Double getPercentage(String staffId, String courseId);

    /**
     * 更新已观看小节数
     * @param courseId 课程id
     * @param staffId 职工id
     * @return 是否成功
     */
    boolean updateLearningVideoNum(String courseId, String staffId);
}
