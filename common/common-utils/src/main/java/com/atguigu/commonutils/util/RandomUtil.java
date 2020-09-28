package com.atguigu.commonutils.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-24 12:25
 */
public class RandomUtil {

    private static final Random random = new Random();

    private static final DecimalFormat fourdf = new DecimalFormat("0000");

    private static final DecimalFormat sixdf = new DecimalFormat("000000");

    public static String getFourBitRandom() {
        return fourdf.format(random.nextInt(10000));
    }

    public static String getSixBitRandom() {
        return sixdf.format(random.nextInt(1000000));
    }

    /**
     * 给定数组，抽取n个数据
     * @param list
     * @param n
     * @return
     */
    public static ArrayList getRandom(List list, int n) {
        Random random = new Random();
        HashMap<Object,Object> hashMap = new HashMap<>();
        // 生成随机数字并存入HashMap
        for (int i = 0; i < list.size(); i++) {
            int number = random.nextInt(100) + 1;
            hashMap.put(number, i);
        }
        // 从HashMap导入数组
        Object[] objects = hashMap.values().toArray();
        ArrayList<Object> array = new ArrayList<>();
        // 遍历数组并打印数据
        for (int i = 0; i < n; i++) {
            array.add(list.get((Integer) objects[i]));
            System.out.print(list.get((Integer) objects[i]) + "\t");
        }
        System.out.println();
        return array;
    }

}
