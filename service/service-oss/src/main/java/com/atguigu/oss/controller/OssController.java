package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-08 21:56
 */
@Api(description="阿里云文件管理")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    // 上传头像的方法
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        // 获取上传文件 MultipartFile
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }

    /**
     * 上传文件到oos云存储
     * @param file 需要上传的文件
     * @return 返回文件在oos的url
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @ApiParam(name = "host", value = "文件上传路径", required = false) String host) {
        String uploadUrl = ossService.upload(file);
        // 返回R对象
        return R.ok().message("文件上传成功！").data("url",uploadUrl);
    }


}
