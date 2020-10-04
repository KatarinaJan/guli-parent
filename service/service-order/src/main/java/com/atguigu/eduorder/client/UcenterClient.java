package com.atguigu.eduorder.client;

import com.atguigu.servicebase.dto.MemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-29 10:46
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @GetMapping("/educenter/ucenter/front/pay/{memberId}")
    MemberDto getMemberInfo(@PathVariable("memberId") String memberId);
}
