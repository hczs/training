package com.hc.learning.controller;


import com.hc.common.utils.TrainingResult;
import com.hc.learning.entity.vo.TreeCategory;
import com.hc.learning.service.CourseCategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-02-24
 */
@RestController
@RequestMapping("/course-category")
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService categoryService;

    /**
     * 添加课程分类接口
     * @param file  课程分类excel文件
     * @return  返回统一格式
     */
    @PostMapping("/import")
    @ApiOperation("导入课程分类数据")
    public TrainingResult importCategories(MultipartFile file) {
        try {
            categoryService.importCategory(file, categoryService);
            return TrainingResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return TrainingResult.error().message("导入失败");
        }
    }

    @GetMapping("/treeList")
    @ApiOperation("获取课程分类数据")
    public TrainingResult getTreeList(){
        List<TreeCategory> treeCategories = categoryService.getTreeCategory();
        return TrainingResult.ok().data("items", treeCategories);
    }
}

