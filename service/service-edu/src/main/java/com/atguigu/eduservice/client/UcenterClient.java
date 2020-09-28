package com.atguigu.eduservice.client;

import com.atguigu.eduservice.entity.vo.UcenterMemberPay;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-28 12:48
 */
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {

    // 根据用户id获取用户信息
    @GetMapping("/educenter/ucenter/getInfoUc/{memberId}")
    UcenterMemberPay getUcenterMember(@PathVariable("memberId") String memberId);

}
