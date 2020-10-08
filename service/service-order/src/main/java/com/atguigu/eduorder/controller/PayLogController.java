package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Jan
 * @since 2020-09-29
 */
@Api(description = "支付日志控制器")
//@CrossOrigin
@RestController
@RequestMapping("/eduorder/pay")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @ApiOperation(value = "生成二维码")
    @GetMapping("generateQrCode/{orderNo}")
    public R generateQrCode(@PathVariable String orderNo) {
        return R.ok().data(payLogService.generateQrCode(orderNo));
    }

    @ApiOperation(value = "更新订单表并追加到订单日志表")
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        // 调用查询接口
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (null == map) {
            return R.error().message("支付出错");
        }
        if (map.getOrDefault("trade_state","default").equals("SUCCESS")) {
            // 更新订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功！");
        }
        return R.ok().code(25000).message("支付中");
    }

}

