package com.atguigu.staservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-30 11:14
 */
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {

    @GetMapping("/educenter/ucenter/countRegisterByDay/{day}")
    R countRegisterByDay(@PathVariable("day") String day);

}
