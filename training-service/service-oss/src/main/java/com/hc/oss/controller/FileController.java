package com.hc.oss.controller;

import com.hc.common.utils.TrainingResult;
import com.hc.oss.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：hc
 * @date ：Created in 2021/2/23 11:57
 * @modified By：
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("upload")
    @ApiOperation("文件上传")
    public TrainingResult uploadFile(MultipartFile file) {
        String url = fileService.uploadFile(file);
        if (StringUtils.isEmpty(url)) {
            return TrainingResult.error().message("服务器错误，上传失败！");
        } else {
            return TrainingResult.ok().data("url", url);
        }
    }
}
