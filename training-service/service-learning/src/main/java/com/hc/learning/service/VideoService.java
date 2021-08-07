package com.hc.learning.service;

import com.hc.learning.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.learning.entity.vo.VideoVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houcheng
 * @since 2021-02-26
 */
public interface VideoService extends IService<Video> {


    /**
     * 获取小节总时长
     * @param videoId 小节id
     * @return 总时长
     */
    Float getDuration(String videoId);

    /**
     * 删除小节表中数据以及云端视频数据
     * @param video 小节对象
     * @return 是否成功
     */
    boolean deleteVideo(Video video);

    /**
     * 根据课程id删除小节
     * @param courseId 课程id
     * @return 是否成功
     */
    boolean deleteVideoByCourseId(String courseId);

    /**
     * 更新小节信息
     * @param videoVo videoVo对象
     * @return 是否成功
     */
    boolean updateVideo(VideoVo videoVo);
}
