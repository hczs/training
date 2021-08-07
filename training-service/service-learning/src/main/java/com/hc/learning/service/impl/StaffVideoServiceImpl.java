package com.hc.learning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.learning.entity.StaffVideo;
import com.hc.learning.mapper.StaffVideoMapper;
import com.hc.learning.service.StaffCourseService;
import com.hc.learning.service.StaffVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.learning.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houcheng
 * @since 2021-03-11
 */
@Service
public class StaffVideoServiceImpl extends ServiceImpl<StaffVideoMapper, StaffVideo> implements StaffVideoService {


    @Autowired
    private VideoService videoService;

    @Autowired
    private StaffCourseService staffCourseService;

    @Override
    public boolean recordAllTime(String staffId, String videoId, Float allTime) {
        // 先查询出上播放的总时长（员工重复学习也会添加学习时长）
        QueryWrapper<StaffVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("video_id", videoId);
        wrapper.eq("staff_id", staffId);
        StaffVideo staffVideo = baseMapper.selectOne(wrapper);
        int line = 0;
        if (staffVideo == null) {
            // 查不到记录代表是第一次观看，就插入数据即可
            StaffVideo sv = new StaffVideo();
            sv.setVideoId(videoId);
            sv.setStaffId(staffId);
            sv.setDuration(videoService.getDuration(videoId));
            sv.setLearningTime(allTime);
            line = baseMapper.insert(sv);
        } else {
            // 找到的话就把当前的总学习时长加到上次的学习时长上
            staffVideo.setLearningTime(staffVideo.getLearningTime() + allTime);
            line = baseMapper.updateById(staffVideo);
        }
        return line > 0;
    }

    @Override
    public boolean recordLastTime(String staffId, String videoId, Float lastTime) {
        // 这个直接更新播放进度就行
        StaffVideo staffVideo = new StaffVideo();
        staffVideo.setLastTime(lastTime);
        QueryWrapper<StaffVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("video_id", videoId);
        wrapper.eq("staff_id", staffId);
        int line = baseMapper.update(staffVideo, wrapper);
        return line > 0;
    }

    @Override
    public Float getLastTime(String staffId, String videoId) {
        // 条件查询指定字段（last_time）
        QueryWrapper<StaffVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("video_id", videoId);
        wrapper.eq("staff_id", staffId);
        StaffVideo staffVideo = baseMapper.selectOne(wrapper.select("last_time"));
        return staffVideo != null ? staffVideo.getLastTime() : null;
    }

    @Override
    public Integer getLearning(String staffId) {
        return baseMapper.getLearning(staffId);
    }

    @Override
    public boolean accomplishVideo(String courseId, String videoId, String staffId) {
        QueryWrapper<StaffVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("video_id", videoId);
        wrapper.eq("staff_id", staffId);
        StaffVideo staffVideo = baseMapper.selectOne(wrapper);
        // 如果这个小节已经完成了，就不用设置了
        if (staffVideo.getAccomplishFlag() == 1) {
            return true;
        } else {
            // 更新对应课程已学习的小节数
            boolean success = staffCourseService.updateLearningVideoNum(courseId, staffId);
            if (success) {
                // 更新小节，设置小节已完成
                staffVideo.setAccomplishFlag(1);
                return baseMapper.updateById(staffVideo) > 0;
            }
        }
        return false;
    }
}
