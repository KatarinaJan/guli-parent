package com.atguigu.eduservice.client;

import org.springframework.stereotype.Component;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-29 17:55
 */
@Component
public class OrderClientImpl implements OrderClient {

    @Override
    public boolean isBuyCourse(String memberid, String courseid) {
        return false;
    }
}
