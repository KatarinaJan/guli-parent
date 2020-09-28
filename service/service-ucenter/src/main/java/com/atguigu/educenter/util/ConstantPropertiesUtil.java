package com.atguigu.educenter.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-25 16:17
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${wx.open.app_id}")
    private String appid;

    @Value("${wx.open.redirect_url}")
    private String redirect_uri;

    @Value("${wx.open.app_secret}")
    private String secret;


    public static String APP_ID;
    public static String SECRET;
    public static String REDIRECT_URI;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appid;
        SECRET = secret;
        REDIRECT_URI = redirect_uri;

    }
}
