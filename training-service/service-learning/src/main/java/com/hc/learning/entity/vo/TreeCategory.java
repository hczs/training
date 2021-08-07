package com.hc.learning.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：hc
 * @date ：Created in 2021/2/24 17:11
 * @modified By：
 */
@Data
public class TreeCategory {
    private String id;
    private String title;

    private List<TreeCategory> children = new ArrayList<>();
}
