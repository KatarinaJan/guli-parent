package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.VideoInfoVo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Jan
 * @since 2020-09-11
 */
@Api(description = "课时管理")
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @ApiOperation(value = "新增课时")
    @PostMapping("info")
    public R save(
            @ApiParam(name = "videoInfo", value = "课时对象", required = true)
            @RequestBody VideoInfoVo videoInfoVo) {
        videoService.saveVideoInfo(videoInfoVo);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("info/{id}")
    public R getVideoInfoById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id) {
        VideoInfoVo videoInfovo = videoService.getVideoInfoById(id);
        return R.ok().data("item", videoInfovo);
    }

    @ApiOperation(value = "更新课时")
    @PutMapping("info/{id}")
    public R updateVideoInfoById(
            @ApiParam(name = "VideoInfoVo", value = "课时基本信息", required = true)
            @RequestBody VideoInfoVo videoInfoVo,
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id) {
        videoService.updateVideoInfoById(videoInfoVo);
        return R.ok();
    }

    @ApiOperation(value = "根据ID删除课时")
    @DeleteMapping("info/{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id) {
        boolean result = videoService.removeVideoById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("删除失败");
        }
    }

}

