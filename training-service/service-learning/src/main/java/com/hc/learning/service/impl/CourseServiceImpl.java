package com.hc.learning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.common.exception.TrainingException;
import com.hc.learning.entity.Course;
import com.hc.learning.entity.StaffCourse;
import com.hc.learning.entity.vo.CoursePublishVo;
import com.hc.learning.entity.vo.CourseQuery;
import com.hc.learning.entity.vo.CourseVo;
import com.hc.learning.entity.vo.front.CourseInfoFrontVo;
import com.hc.learning.mapper.CourseMapper;
import com.hc.learning.service.ChapterService;
import com.hc.learning.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.learning.service.StaffCourseService;
import com.hc.learning.service.VideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houcheng
 * @since 2021-02-24
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private StaffCourseService staffCourseService;

    @Autowired
    private VideoService videoService;

    @Override
    public void pageQuery(Page<Course> pageParam, CourseQuery courseQuery) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        // 临时创建的课程不查询
        wrapper.ne("status","Provisional");
        if (courseQuery != null) {
            // 讲师查询
            if (StringUtils.isNotEmpty(courseQuery.getTeacherId())) {
                wrapper.eq("teacher_id", courseQuery.getTeacherId());
            }
            // 课程分类查询
            if (StringUtils.isNotEmpty(courseQuery.getCategoryId())) {
                wrapper.eq("category_id", courseQuery.getCategoryId());
            }
            if (StringUtils.isNotEmpty(courseQuery.getCategoryParentId())) {
                wrapper.eq("category_parent_id", courseQuery.getCategoryParentId());
            }
            // 课程名称模糊查询
            if (StringUtils.isNotEmpty(courseQuery.getTitle())) {
                wrapper.like("title", courseQuery.getTitle());
            }
        }
        // 执行分页查询
        baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public List<Course> getHotCourses() {
        return baseMapper.getHotCourse();
    }

    @Override
    public Map<String, Object> courseFrontQuery(Page<Course> page, CourseQuery courseQuery) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        // 只查询已发布的
        wrapper.eq("status","Normal");
        if (!org.springframework.util.StringUtils.isEmpty(courseQuery.getTitle())) {
            wrapper.like("title", courseQuery.getTitle());
        }
        if (!org.springframework.util.StringUtils.isEmpty(courseQuery.getCategoryParentId())) {
            wrapper.eq("category_parent_id", courseQuery.getCategoryParentId());
        }
        if (!org.springframework.util.StringUtils.isEmpty(courseQuery.getCategoryId())) {
            wrapper.eq("category_id", courseQuery.getCategoryId());
        }
        baseMapper.selectPage(page, wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("courses", page.getRecords());
        map.put("total", page.getTotal());
        map.put("current", page.getCurrent());
        map.put("pages", page.getPages());
        map.put("size", page.getSize());
        map.put("hasNext", page.hasNext());
        map.put("hasPre", page.hasPrevious());
        return map;
    }

    @Override
    public CourseInfoFrontVo getCourseInfoFrontVoById(String courseId) {
        // 更新浏览量
        updateCourseView(courseId);
        return baseMapper.getCourseInfoFrontById(courseId);
    }

    @Override
    public String addCourse(CourseVo courseVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseVo, course);
        // 返回影响的行数
        int line = baseMapper.insert(course);
        if (line > 0) {
            return course.getId();
        } else {
            throw new TrainingException(500, "服务器错误，课程添加失败！");
        }
    }

    @Override
    public boolean updateCourse(CourseVo courseVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseVo, course);
        int line = baseMapper.updateById(course);
        return line > 0;
    }

    @Override
    public boolean updateCommentNum(String courseId) {
        Course course = baseMapper.selectById(courseId);
        course.setCommentNum(course.getCommentNum() + 1);
        int line = baseMapper.updateById(course);
        return line > 0;
    }

    @Override
    public boolean joinCourse(String staffId, String courseId) {
        // 更新课程表参与人数
        Course course = baseMapper.selectById(courseId);
        course.setLearningNum(course.getLearningNum()+1);
        int courseLine = baseMapper.updateById(course);
        // 调用staffCourseService添加记录
        StaffCourse staffCourse = new StaffCourse();
        staffCourse.setCourseId(courseId);
        staffCourse.setStaffId(staffId);
        staffCourse.setAllVideoNum(course.getLessonNum());
        boolean success = staffCourseService.save(staffCourse);
        return courseLine > 0 && success;
    }

    @Override
    public CoursePublishVo getCoursePublishInfo(String id) {
        return baseMapper.getCoursePublishById(id);
    }

    @Override
    public boolean publishCourse(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        int line = baseMapper.updateById(course);
        return line > 0;
    }

    @Override
    public boolean savePublishCourse(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus("Draft");
        int line = baseMapper.updateById(course);
        return line > 0;
    }

    @Override
    public boolean deleteCourse(String id) {
        // 删除此课程下的小节信息
        boolean videoSuccess = videoService.deleteVideoByCourseId(id);
        // 删除章节信息
        boolean chapterSuccess = chapterService.deleteChapterByCourseId(id);
        // 删除课程信息
        int line = baseMapper.deleteById(id);
        return chapterSuccess && videoSuccess && line > 0;
    }

    /**
     * 更新浏览量
     * @param courseId 课程id
     */
    private void updateCourseView(String courseId) {
        Course course = baseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }
}
