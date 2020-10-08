package com.atguigu.edumsm.controller;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.util.RandomUtil;
import com.atguigu.edumsm.service.MsmService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-24 09:34
 */
@Api(description = "阿里云短信服务")
@RestController
@RequestMapping("/edumsm/api")
//@CrossOrigin
public class MsmApiController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "/send/{phone}")
    public R code(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok().message("验证码已缓存至Redis");
        }
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.isSend(phone, "SMS_203191836", param);
        if (isSend) {
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok().message("发送短信成功");
        } else {
            return R.error().message("发送短信失败");
        }
    }
}
