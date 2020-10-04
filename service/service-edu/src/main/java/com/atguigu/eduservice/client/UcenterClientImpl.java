package com.atguigu.eduservice.client;

import com.atguigu.eduservice.entity.vo.MemberDto;
import com.atguigu.eduservice.entity.vo.UcenterMemberPay;
import org.springframework.stereotype.Component;

/**
 * @Project: guli-parent
 * @Describe: 熔断器
 * @Author: Jan
 * @Date: 2020-09-28 12:51
 */
@Component
public class UcenterClientImpl implements UcenterClient {

    @Override
    public MemberDto getMemberInfo(String memberId) {
        return null;
    }
}
