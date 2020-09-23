package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Project: guli-parent
 * @Describe: 读取配置文件
 * @Author: Jan
 * @Date: 2020-09-08 21:47
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    // 读取配置文件内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endPoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    // 创建公开变量
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    /**
     * 初始化配置信息，这个方法将在所有的属性被初始化后调用
     * @throws Exception 继承父
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 赋值
        END_POINT = this.endPoint;
        KEY_ID = this.keyId;
        KEY_SECRET = this.keySecret;
        BUCKET_NAME = this.bucketName;
    }
}
