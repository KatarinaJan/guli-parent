package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ResultCode;
import com.atguigu.servicebase.exception.GuliException;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Jan
 * @since 2020-09-30
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void countRegisterByDay(String day) {
        // 先清空之前已存在某一天的统计对象
        baseMapper.delete(new QueryWrapper<StatisticsDaily>().eq("date_calculated", day));
        // 调用会员控制器的查询注册人数接口
        R r = ucenterClient.countRegisterByDay(day);
        if (null == r) {
            throw new GuliException(ResultCode.ERROR, "查询注册人数出错！");
        }
        Integer countRegister = (Integer) r.getData().get("countRegister");

        StatisticsDaily daily = new StatisticsDaily();
        daily.setDateCalculated(day); // 统计日期
        daily.setRegisterNum(countRegister); // 注册人数
        // 假造点数据
        daily.setLoginNum(RandomUtils.nextInt(1000, 2000)); // 登录人数
        daily.setVideoViewNum(RandomUtils.nextInt(10000,20000)); // 每日播放视频数
        daily.setCourseNum(RandomUtils.nextInt(100, 200)); // 每日新增课程数

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> showChart(String begin, String end, String type) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        // 只查询某一维度列和日期列
        wrapper.select(type, "date_calculated");
        // 选择时间范围
        wrapper.between("date_calculated", begin, end);
        // 查询
        List<StatisticsDaily> dayList = baseMapper.selectList(wrapper);

        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);

        for (int i = 0; i < dayList.size(); i++) {
            StatisticsDaily daily = dayList.get(i);
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        return map;
    }
}
