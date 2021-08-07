package com.hc.learning.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.learning.entity.CourseCategory;
import com.hc.learning.entity.excel.CategoryExcel;
import com.hc.learning.service.CourseCategoryService;

/**
 * @author ：hc
 * @date ：Created in 2021/2/24 16:30
 * @modified By：
 */
public class CategoryListener extends AnalysisEventListener<CategoryExcel> {

    /**
     * 此类不能被Spring托管,所以不能直接引注入service进行操作数据库,所以得自己加个service参数
     * 自己加的service参数,可以从外面传入,然后在监听器中操作数据库
     */
    private CourseCategoryService courseCategoryService;

    public CategoryListener() {
    }

    public CategoryListener(CourseCategoryService courseCategoryService) {
        this.courseCategoryService = courseCategoryService;
    }

    @Override
    public void invoke(CategoryExcel data, AnalysisContext context) {
        // 读取数据,将数据存入数据库,存储之前要先查找分类是否已存在

        // 读取数据
        String oneTittle = data.getOneCategory();
        // 查询是否存在
        CourseCategory one = existOneCategory(oneTittle);
        // 3.插入数据库，当查询出来的结果为空的时候
        if (one == null){
            CourseCategory category = new CourseCategory();
            category.setCategoryName(oneTittle);
            category.setParentId("0");
            courseCategoryService.save(category);
        }

        // 查询父id
        String pid = existOneCategory(oneTittle).getId();

        // 读取第二行数据,此时说明父类这个大类是存在的
        String twoTittle = data.getTwoCategory();
        // 查询是否存在
        CourseCategory two = existTwoCategory(twoTittle, pid);
        // 不存在即可进行存储操作
        if (two == null){
            CourseCategory category = new CourseCategory();
            category.setCategoryName(twoTittle);
            category.setParentId(pid);
            courseCategoryService.save(category);
        }
    }

    /**
     * 查看一级分类是否重复
     * @param tittle 分类名称
     * @return Subject对象
     */
    private CourseCategory existOneCategory(String tittle){
        // 封装查询条件
        QueryWrapper<CourseCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("category_name", tittle);
        wrapper.eq("parent_id", "0");
        // 执行查询返回结果
        return courseCategoryService.getOne(wrapper);
    }

    /**
     * 查看二级分类是否重复
     * @param tittle 分类名称
     * @param pid 父id
     * @return Subject对象
     */
    private CourseCategory existTwoCategory(String tittle,String pid){
        // 封装查询条件，这里要查询的是名称相同而且父id也相同的数据
        QueryWrapper<CourseCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("category_name", tittle);
        wrapper.eq("parent_id", pid);
        // 执行查询返回结果
        return courseCategoryService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
