package com.hc.learning.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author ：hc
 * @date ：Created in 2021/2/24 16:21
 * @modified By：
 */
@Data
public class CategoryExcel {
    /**
     * 一级分类
     */
    @ExcelProperty(index = 0)
    private String oneCategory;

    /**
     * 二级分类
     */
    @ExcelProperty(index = 1)
    private String twoCategory;
}
