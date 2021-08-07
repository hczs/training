package com.hc.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hc.oss.service.FileService;
import com.hc.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author ：hc
 * @date ：Created in 2021/2/23 11:52
 * @modified By：
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(MultipartFile file) {
        // 获取相关参数
        String endpoint = ConstantPropertiesUtil.ENDPOINT;
        String accessKeyId = ConstantPropertiesUtil.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 上传文件流
            InputStream inputStream = file.getInputStream();
            // 构建日期路径： avatar/2020/11/30
            String filePath = new DateTime().toString("yyyy/MM/dd");
            // 文件名：uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;
            // 拼接图片url并返回
            String fileUrl = "http://" + bucketName + "." + endpoint + "/" + filePath + "/" + newName;
            // 上传
            ossClient.putObject(bucketName,filePath+"/"+newName,inputStream);
            // 关闭client
            ossClient.shutdown();
            return fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
