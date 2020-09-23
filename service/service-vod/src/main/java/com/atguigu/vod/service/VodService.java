package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-21 13:19
 */
public interface VodService {

    /**
     * 上传文件到阿里云视频点播
     * @param file 视频文件
     * @return 视频id和name
     */
    String uploadVideo(MultipartFile file);

    /**
     * 删除视频
     * @param videoId 视频id
     */
    void removeVideo(String videoId);

    /**
     * 批量删除视频
     * @param videoIdList 视频id list
     */
    void removeVideoList(List<String> videoIdList);
}
