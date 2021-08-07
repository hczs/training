package com.hc.learning.service;

import com.hc.learning.entity.CourseCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.learning.entity.vo.TreeCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houcheng
 * @since 2021-02-24
 */
public interface CourseCategoryService extends IService<CourseCategory> {

    /**
     * 通过文件导入课程分类信息
     * @param file  课程分类文件
     * @param courseCategoryService service
     */
    void importCategory(MultipartFile file, CourseCategoryService courseCategoryService);

    /**
     * 获取树形结构的课程分类数据
     * @return 树形结构数据
     */
    List<TreeCategory> getTreeCategory();

}
