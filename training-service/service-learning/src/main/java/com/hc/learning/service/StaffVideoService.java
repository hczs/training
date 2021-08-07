package com.hc.learning.service;

import com.hc.learning.entity.StaffVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houcheng
 * @since 2021-03-11
 */
public interface StaffVideoService extends IService<StaffVideo> {


    /**
     * 记录指定员工观看某个小节的总时长
     * @param staffId 员工id
     * @param videoId 小节id
     * @param allTime 总时长
     * @return 是否成功
     */
    boolean recordAllTime(String staffId, String videoId, Float allTime);

    /**
     * 记录指定员工观看某个小节的上次播放进度
     * @param staffId 员工id
     * @param videoId 小节id
     * @param lastTime 上次观看进度
     * @return 是否成功
     */
    boolean recordLastTime(String staffId, String videoId, Float lastTime);

    /**
     * 获取员工观看进度
     * @param staffId 员工id
     * @param videoId 小节id
     * @return 上次播放到了多场时间（s）
     */
    Float getLastTime(String staffId, String videoId);

    /**
     * 查询某个员工的总学习时长
     * @param staffId 员工id
     * @return 学习总时长
     */
    Integer getLearning(String staffId);

    /**
     * 指定用户，指定小节，观看完成处理方法，更新相应字段
     * @param courseId 课程id
     * @param videoId 小节id
     * @param staffId 职工id
     * @return 是否成功
     */
    boolean accomplishVideo(String courseId, String videoId, String staffId);
}
