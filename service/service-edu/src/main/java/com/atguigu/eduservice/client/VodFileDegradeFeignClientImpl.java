package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Project: guli-parent
 * @Describe: 熔断器实现类
 * @Author: Jan
 * @Date: 2020-09-22 14:08
 */
@Component
public class VodFileDegradeFeignClientImpl implements VodClient{
    @Override
    public R removeVideo(String videoId) {
        return R.error().message("time out");
    }

    @Override
    public R removeVideoList(List<String> videoIdList) {
        return R.error().message("time out");
    }
}
