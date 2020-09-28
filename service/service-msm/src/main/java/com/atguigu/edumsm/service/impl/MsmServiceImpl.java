package com.atguigu.edumsm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.edumsm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-24 09:35
 */
@Service
public class MsmServiceImpl implements MsmService {
    /**
     * 发送短信
     */
    @Override
    public boolean isSend(String phoneNumbers, String templateCode, Map<String, Object> param) {
        if (StringUtils.isEmpty(phoneNumbers)) {
            return false;
        }

        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI4G241mS6dJwt5sEZfkaC", "t57FYd0OzthNcRHlV2JoLsoBiRerLH");
        DefaultAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
//        request.setSysProtocol(ProtocolType.HTTPS);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        // 接收短信的手机号码
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        // 短信签名名称。请在控制台签名管理页面签名名称一列查看（必须是已添加、并通过审核的短信签名）。
        request.putQueryParameter("SignName", "我的谷粒在线教育网站");
        // 短信模板ID
        // 短信类型。0：验证码；1：短信通知；2：推广短信；3：国际/港澳台消息
        request.putQueryParameter("TemplateCode", templateCode);
        // 短信模板变量对应的实际值，JSON格式。
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

}
