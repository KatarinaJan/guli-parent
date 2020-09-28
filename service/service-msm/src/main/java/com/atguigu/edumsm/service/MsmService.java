package com.atguigu.edumsm.service;

import java.util.Map;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-24 09:35
 */
public interface MsmService {

    boolean isSend(String phoneNumbers, String templateCode, Map<String,Object> param);

}
