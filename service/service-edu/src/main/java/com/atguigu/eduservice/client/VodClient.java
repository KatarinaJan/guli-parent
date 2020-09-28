package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Project: guli-parent
 * @Describe: feign服务调用
 * @Author: Jan
 * @Date: 2020-09-22 10:21
 */

//@FeignClient("service-vod")
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClientImpl.class)
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/video/{videoId}")
    public R removeVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/eduvod/video/batch")
    public R removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);

}
