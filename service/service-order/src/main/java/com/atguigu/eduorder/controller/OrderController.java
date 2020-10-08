package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Jan
 * @since 2020-09-29
 */
@Api(description = "前台视频订单控制器")
//@CrossOrigin
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "根据课程id和用户id创建订单，返回订单id")
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        String orderNo = orderService.saveOrderInfo(courseId, request);
        if (null == orderNo || orderNo.equals("")) {
            return R.error().message("生成订单信息失败！");
        }
        return R.ok().data("orderNo", orderNo).message("生成订单成功!");
    }

    @ApiOperation(value = "根据订单编号获取订单")
    @GetMapping("{orderNo}")
    public R getOrderByOrderNo(@PathVariable String orderNo) {
        Order order = orderService.getOne(new LambdaQueryWrapper<Order>().eq(Order::getOrderNo, orderNo));
        if (null == order) {
            return R.error().message("没有查询到当前订单信息！");
        }
        return R.ok().data("order", order);
    }

    @ApiOperation(value = "根据用户id和课程id查询订单信息")
    @GetMapping("isBuyCourse/{memberid}/{courseid}")
    public boolean isBuyCourse(@PathVariable String memberid, @PathVariable String courseid) {
        int count = orderService.count(new QueryWrapper<Order>().eq("member_id", memberid).eq("course_id", courseid).eq("status", 1));
        return count > 0;
    }

}

