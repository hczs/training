package com.hc.learning.entity.vo;

import lombok.Data;

/**
 * 讲师查询条件封装
 * @author hczs8
 */
@Data
public class TeacherQuery {
    private String name;
    private String begin;
    private String end;
}
