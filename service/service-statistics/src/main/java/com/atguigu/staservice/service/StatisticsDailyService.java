package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Jan
 * @since 2020-09-30
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void countRegisterByDay(String day);

    Map<String, Object> showChart(String begin, String end, String type);

}
