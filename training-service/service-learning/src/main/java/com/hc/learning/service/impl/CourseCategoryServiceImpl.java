package com.hc.learning.service.impl;

import com.alibaba.excel.EasyExcel;
import com.hc.learning.entity.CourseCategory;
import com.hc.learning.entity.excel.CategoryExcel;
import com.hc.learning.entity.vo.TreeCategory;
import com.hc.learning.listener.CategoryListener;
import com.hc.learning.mapper.CourseCategoryMapper;
import com.hc.learning.service.CourseCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houcheng
 * @since 2021-02-24
 */
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {

    @Override
    public void importCategory(MultipartFile file, CourseCategoryService courseCategoryService) {
        try{
            InputStream inputStream = file.getInputStream();
            // 使用easyExcel读取文件数据,通过监听器读取的时候同步存储到数据库中
            EasyExcel.read(inputStream, CategoryExcel.class, new CategoryListener(courseCategoryService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<TreeCategory> getTreeCategory() {
        // 查询所有数据
        List<CourseCategory> categories = baseMapper.selectList(null);
        return getTreeCategoryList(categories, "0");
    }

    /**
     * 递归解析包装课程分类数据
     * @param categories 课程分类list
     * @param parentId 父类id
     * @return List<TreeCategory>
     */
    private List<TreeCategory> getTreeCategoryList(List<CourseCategory> categories, String parentId) {
        ArrayList<TreeCategory> treeCategoryArrayList = new ArrayList<>();
        // 先把一级的查出来
        for (CourseCategory category : categories){
            if (parentId.equals(category.getParentId())){
                TreeCategory treeCategory = new TreeCategory();
                treeCategory.setId(category.getId());
                treeCategory.setTitle(category.getCategoryName());
                // 递归查询子类放入
                treeCategory.setChildren(getTreeCategoryList(categories,category.getId()));
                // 放入大集合
                treeCategoryArrayList.add(treeCategory);
            }
        }
        return treeCategoryArrayList;
    }
}
