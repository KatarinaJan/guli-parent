package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ResultCode;
import com.atguigu.servicebase.exception.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.util.AliyunVodSDKUtil;
import com.atguigu.vod.util.ConstanPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-21 13:18
 */
@Api(description = "阿里云视频点播微服务")
@RestController
//@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation("视频上传")
    @PostMapping("upload")
    public R uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file")MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId).message("上传视频成功！");
    }

    @ApiOperation("删除视频")
    @DeleteMapping("{videoId}")
    public R removeVideo(
            @ApiParam(name = "videoId", value = "云端视频id", required = true)
            @PathVariable String videoId) {
        vodService.removeVideo(videoId);
        return R.ok().message("删除视频成功！");
    }

    @ApiOperation("批量删除视频")
    @DeleteMapping("batch")
    public R removeVideoList(
            @ApiParam(name = "videoIdList", value = "云端视频ids", required = true)
            @RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeVideoList(videoIdList);
        return R.ok().message("批量删除视频成功！");
    }

    @ApiOperation("获取播放视频凭证")
    @GetMapping("{videoId}")
    public R getVideoPlayAuth(
            @ApiParam(name = "videoId", value = "云端视频id", required = true)
            @PathVariable("videoId") String videoId) {
        // 获取阿里云存储相关常量
        String accessKeyId = ConstanPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstanPropertiesUtil.ACCESS_KEY_SECRET;
        // 初始化
        DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(accessKeyId, accessKeySecret);
        // 请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        // 响应
        GetVideoPlayAuthResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR, "获取凭证失败！");
        }
        // 获取播放凭证
        String playAuth = response.getPlayAuth();
        // 返回
        return R.ok().data("playAuth", playAuth).message("获取凭证成功！");
    }

}