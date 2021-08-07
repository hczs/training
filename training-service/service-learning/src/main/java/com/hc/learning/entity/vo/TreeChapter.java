package com.hc.learning.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：hc
 * @date ：Created in 2020/12/19 18:09
 */
@Data
public class TreeChapter {
    private String id;
    private String title;

    /**
     * 章节下面放的小节内容
     */
    private List<TreeVideo> children = new ArrayList<>();
}
