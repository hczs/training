package com.hc.learning.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * VideoVo包装类
 * @author hczs8
 */
@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "小节id")
    private String id;

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "章节id")
    private String chapterId;

    @ApiModelProperty(value = "小节标题")
    private String title;

    @ApiModelProperty(value = "小节视频id（阿里云视频id）")
    private String videoSourceId;

    @ApiModelProperty(value = "视频时长（秒）")
    private Float duration;

    @ApiModelProperty(value = "视频大小（字节）")
    private Long size;

    @ApiModelProperty(value = "原始文件名称")
    private String videoOriginalName;

}
