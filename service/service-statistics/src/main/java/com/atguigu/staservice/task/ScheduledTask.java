package com.atguigu.staservice.task;

import com.atguigu.commonutils.util.DateUtil;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Project: guli-parent
 * @Describe: 七域表达式 https://cron.qqe2.com/
 * @Author: Jan
 * @Date: 2020-09-30 12:02
 */
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

//    @Scheduled(cron = "0/5 * * * * *")
    public void testTask() {
        System.out.println(System.currentTimeMillis() + "***定时执行***");
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void countRegisterByDayTask() {
        // 获取当天的前一天
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.countRegisterByDay(day);
    }
}
