package com.atguigu.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.commonutils.util.HttpClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.entity.PayLog;
import com.atguigu.eduorder.mapper.PayLogMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author Jan
 * @since 2020-09-29
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    public OrderService orderService;

    @Override
    public Map<String, Object> generateQrCode(String orderNo) {
        // 根据订单id获取订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        Map<String, String> m = new HashMap<>();
        // 设置支付参数
        m.put("appid","wx74862e0dfcf69954");
        m.put("mch_id","1558950191");
        m.put("nonce_str",WXPayUtil.generateNonceStr());
        m.put("body",order.getCourseTitle());
        m.put("out_trade_no",orderNo);
        m.put("total_fee",order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
        m.put("spbill_create_ip","127.0.0.1");
        m.put("notify_url","http://guli.shop/api/order/weixinPay/weixinNotify\n");
        m.put("trade_type","NATIVE");


        Map<String, String> resultMap = null;
        try {
        // HTTPClient来根据URL访问第三方接口并传递参数
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        // client设置参数
        client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
        client.setHttps(true);
        client.post();
        // 返回第三方数据
        String xml = client.getContent();
        resultMap = WXPayUtil.xmlToMap(xml);
        // System.out.println("resultMap:" + resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }


        // 封装结果集
        Map<String, Object> map = new HashMap<>();
        map.put("out_trade_no", orderNo); // 商户订单号
        map.put("course_id", order.getCourseId());
        map.put("total_fee", order.getTotalFee()); // 标价金额
        map.put("result_code", resultMap.get("result_code")); // 业务结果
        map.put("code_url", resultMap.get("code_url")); // 二维码链接

        // 微信支付二维码2小时过期，可采取2小时未支付取消订单
        //redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);


        return map;
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        Map<String, String> resultMap = null;
        // 封装参数
        Map<String, String> m = new HashMap<>();
        m.put("appid","wx74862e0dfcf69954");
        m.put("mch_id","1558950191");
        m.put("nonce_str",WXPayUtil.generateNonceStr());
        m.put("out_trade_no",orderNo);
        // 设置请求
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            // 返回第三方数据
            String xml = client.getContent();
            resultMap = WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
        System.out.println(resultMap);
        // 返回
        return resultMap;
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        // 获取订单id
        String orderNo = map.get("out_trade_no");
        // 根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        if (order.getStatus() == 1) {
            return;
        }
        // 更新订单状态字段
        order.setStatus(1);
        // 更新表
        orderService.updateById(order);


        // 记录支付日志
        PayLog payLog = new PayLog();
        payLog.setOrderNo(order.getOrderNo());
        payLog.setPayTime(new Date());
        payLog.setTotalFee(order.getTotalFee());
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setTradeState(map.get("trade_state"));
        payLog.setPayType(order.getPayType());
        payLog.setAttr(JSONObject.toJSONString(map));
        // 插入到支付日志表
        baseMapper.insert(payLog);
    }
}
