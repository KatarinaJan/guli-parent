package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author Jan
 * @since 2020-09-29
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 根据订单号生成二维码
     * @param orderNo 订单编号
     * @return 支付日志表所需字段
     */
    Map<String, Object> generateQrCode(String orderNo);

    /**
     * 根据订单号查询订单状态
     * @param orderNo 订单编号
     * @return trade_state 状态
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 更新订单信息
     * @param map 原本的信息
     */
    void updateOrderStatus(Map<String, String> map);
}
