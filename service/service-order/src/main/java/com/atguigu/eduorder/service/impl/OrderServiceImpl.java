package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ResultCode;
import com.atguigu.commonutils.util.JwtUtils;
import com.atguigu.eduorder.client.CourseClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.servicebase.dto.CourseInfoDto;
import com.atguigu.servicebase.dto.MemberDto;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.utils.OrderUtil;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Jan
 * @since 2020-09-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CourseClient courseClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String saveOrderInfo(String courseId, HttpServletRequest request) {
        // 创建一个订单实体类
        Order order = new Order();
        // 需要课程信息
        CourseInfoDto courseInfo = courseClient.getCourseInfoDto(courseId);
        BeanUtils.copyProperties(courseInfo, order);
        // 需要会员信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (null == memberId || "".equals(memberId)) {
            throw new GuliException(28004, "请登录");
        }
        MemberDto memberInfo = ucenterClient.getMemberInfo(memberId);
        BeanUtils.copyProperties(memberInfo, order);
        // 需要订单信息
        order.setOrderNo(OrderUtil.getOrderNo());
        order.setPayType(1); // 支付类型（1：微信 2：支付宝）
        order.setStatus(0); // 订单状态（0：未支付 1：已支付）
        // 保存
        if (baseMapper.insert(order) == 0) {
            throw new GuliException(ResultCode.ERROR, "保存订单失败！");
        }
        // 返回订单编号
        return order.getOrderNo();
    }
}
