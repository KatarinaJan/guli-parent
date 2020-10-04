package com.atguigu.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-29 17:54
 */
@Component
@FeignClient(name = "service-order", fallback = OrderClientImpl.class)
public interface OrderClient {
    // 查询订单信息
    @GetMapping("/eduorder/order/isBuyCourse/{memberid}/{courseid}")
    boolean isBuyCourse(@PathVariable("memberid") String memberid, @PathVariable("courseid") String courseid);
}
