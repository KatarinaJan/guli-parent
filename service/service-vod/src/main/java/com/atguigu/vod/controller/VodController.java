package com.atguigu.vod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
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
@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("upload")
    @ApiOperation("视频上传")
    public R uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file")MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId).message("上传视频成功！");
    }

    @DeleteMapping("{videoId}")
    @ApiOperation("删除视频")
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

}