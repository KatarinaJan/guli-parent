package com.atguigu.staservice.mapper;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 网站统计日数据 Mapper 接口
 * </p>
 *
 * @author Jan
 * @since 2020-09-30
 */
public interface StatisticsDailyMapper extends BaseMapper<StatisticsDaily> {

    Integer countRegisterByDay(String day);
}
