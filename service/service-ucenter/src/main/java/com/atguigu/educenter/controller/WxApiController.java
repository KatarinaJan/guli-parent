package com.atguigu.educenter.controller;

import com.atguigu.commonutils.util.HttpClientUtils;
import com.atguigu.commonutils.util.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.util.ConstantPropertiesUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.ConstantCallSite;
import java.util.Map;

/**
 * @Project: guli-parent
 * @Describe: 微信登录API接口
 * @Author: Jan
 * @Date: 2020-09-25 16:26
 */
@Api(description = "微信登录API接口")
//@CrossOrigin
// 由于不需要返回请求体 只需Controller即可
//@RestController
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "获取二维码")
    @GetMapping("/qrcode")
    public String getQrCode() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=state" +
                "#wechat_redirect";
        String url = String.format(baseUrl, ConstantPropertiesUtil.APP_ID, ConstantPropertiesUtil.REDIRECT_URI);
        return "redirect:" + url;
    }

    @ApiOperation(value = "使用微信登录网站")
    @GetMapping("/callback")
    public String callback(@RequestParam String code, @RequestParam String state) {
        // 通过code获取access_token
        String getTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String tokenUrl = String.format(getTokenUrl, ConstantPropertiesUtil.APP_ID, ConstantPropertiesUtil.SECRET, code);
        String response = null;
        try {
            response = HttpClientUtils.get(tokenUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Map map = gson.fromJson(response, Map.class);
        String access_token = String.valueOf(map.get("access_token"));
        String openid = String.valueOf(map.get("openid"));

        UcenterMember member = memberService.getOne(new LambdaQueryWrapper<UcenterMember>().eq(UcenterMember::getOpenid, openid));

        if (null == member) {
            // 通过access_token调用接口获取用户个人信息
            String getInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String infoUrl = String.format(getInfoUrl, access_token, openid);

            try {
                response = HttpClientUtils.get(infoUrl);
                System.out.println(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map = gson.fromJson(response, Map.class);
            String nickname = String.valueOf(map.get("nickname"));
            String headimgurl = String.valueOf(map.get("headimgurl"));
            String sexStr = map.get("sex").toString();  // 1.0
            // 1.0 -> 1
            Integer sex = Integer.parseInt(sexStr.substring(0, sexStr.indexOf(".")));

            member = new UcenterMember();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setSex(sex);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }

        // 生成jwt
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        //因为端口号不同存在蛞蝓问题，cookie不能跨域，所以这里使用url重写
        return "redirect:http://localhost:3000?token=" + token;
    }


}
