package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Jan
 * @since 2020-09-29
 */
public interface OrderService extends IService<Order> {

    /**
     * 保存订单信息、包含课程信息、会员信息、订单信息
     * @param courseId 课程id
     * @param request 会员id
     * @return 订单号
     */
    String saveOrderInfo(String courseId, HttpServletRequest request);

}
