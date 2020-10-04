package com.atguigu.eduorder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-29 11:07
 */
public class OrderUtil {

    /**
     * 时间戳 + 3位随机数生成订单号
     * @return 订单号
     */
    public static String getOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return date + result;
    }
}
