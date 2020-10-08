package com.atguigu.staservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Jan
 * @since 2020-09-30
 */
@Api(description =  "网站统计日数据")
@RestController
//@CrossOrigin
@RequestMapping("/staservice/daily")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation("将会员表统计的注册人数添加到统计表")
    @PostMapping("/{day}")
    public R countRegisterByDay(@PathVariable String day) {
        statisticsDailyService.countRegisterByDay(day);
        return R.ok();
    }

    @ApiOperation("基于开始时间和结束时间展示不同维度的图表")
    @GetMapping("/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin, @PathVariable String end, @PathVariable String type) {
        Map<String, Object> map = statisticsDailyService.showChart(begin, end, type);
        return R.ok().data(map);
    }
}

