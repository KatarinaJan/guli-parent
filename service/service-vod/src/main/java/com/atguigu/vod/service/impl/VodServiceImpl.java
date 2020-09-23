package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.commonutils.ResultCode;
import com.atguigu.servicebase.exception.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.util.AliyunVodSDKUtil;
import com.atguigu.vod.util.ConstanPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-21 13:19
 */
@Service
@Slf4j
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideo(MultipartFile file) {
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException ioException) {
            throw new GuliException(ResultCode.ERROR, "Vod服务上传失败！");
        }
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

        UploadStreamRequest request = new UploadStreamRequest(ConstanPropertiesUtil.ACCESS_KEY_ID, ConstanPropertiesUtil.ACCESS_KEY_SECRET, title, originalFilename, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
        // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
        String videoId = response.getVideoId();
        if (!response.isSuccess()) {
            String errorMessage = "阿里云上传错误：" + "code:" + response.getCode() + ",message:" + response.getMessage();
            log.warn(errorMessage);
            if (StringUtils.isEmpty(videoId)) {
                throw new GuliException(ResultCode.ERROR, errorMessage);
            }
        }

        return videoId;
    }

    @Override
    public void removeVideo(String videoId) {
        DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(ConstanPropertiesUtil.ACCESS_KEY_ID, ConstanPropertiesUtil.ACCESS_KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(videoId);
        try {
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.println("RequestId => " + response.getRequestId());
        } catch (ClientException e) {
           throw new GuliException(ResultCode.ERROR, "视频删除失败！");
        }

    }

    @Override
    public void removeVideoList(List<String> videoIdList) {
        // 初始化
        DefaultAcsClient client =  AliyunVodSDKUtil.initVodClient(ConstanPropertiesUtil.ACCESS_KEY_ID, ConstanPropertiesUtil.ACCESS_KEY_SECRET);
        // 创建请求对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        // 将list转换成string 1,2,3
        String ids = StringUtils.arrayToCommaDelimitedString(videoIdList.toArray());
        request.setVideoIds(ids);
        try {
            // 获取响应
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.println("RequestIds => " + response.getRequestId());
        } catch (ClientException e) {
            throw new GuliException(ResultCode.ERROR, "视频删除失败！");
        }

    }
}
