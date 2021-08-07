package com.hc.learning.service;

import com.hc.learning.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.learning.entity.vo.TreeChapter;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houcheng
 * @since 2021-02-25
 */
public interface ChapterService extends IService<Chapter> {
    /**
     * 获取章节的树形结构list
     * @param courseId 课程id
     * @return 章节树结构list
     */
    List<TreeChapter> getTreeChapter(String courseId);

    /**
     * 查询指定用户学习的章节情况
     * @param userId 用户id
     * @param courseId 课程id
     * @return treeChapter
     */
    List<TreeChapter> getTreeChapter(String userId, String courseId);

    /**
     * 根据id删除对应章节信息
     * 注意：此操作会将此章节下得小节信息同步删除
     * @param id 章节id
     * @return 是否删除成功
     */
    boolean deleteChapter(String id);

    /**
     * 根据课程id删除此课程下的章节信息
     * @param courseId 课程id
     * @return 是否成功
     */
    boolean deleteChapterByCourseId(String courseId);
}
