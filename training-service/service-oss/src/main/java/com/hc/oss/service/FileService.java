package com.hc.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：hc
 * @date ：Created in 2021/2/23 11:51
 * @modified By：
 */
public interface FileService {

    /**
     * 文件上传
     * @param file 文件对象
     * @return 文件访问地址
     */
    String uploadFile(MultipartFile file);
}
