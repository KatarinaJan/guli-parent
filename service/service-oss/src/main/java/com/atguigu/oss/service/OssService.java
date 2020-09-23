package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-08 21:56
 */
public interface OssService {

    /**
     * 上传文件
     * @param file 文件
     * @return url
     */
    String uploadFileAvatar(MultipartFile file);

    /**
     * 上传文件
     * @param file 需要上传的文件
     * @return 返回文件在oos的url
     */
    String upload(MultipartFile file);

}
