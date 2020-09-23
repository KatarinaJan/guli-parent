package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-10 15:49
 */
@Api(description="测试")
@RestController
@RequestMapping("/eduoss/test")
@CrossOrigin
public class TestCotroller {

    @GetMapping
    public R getString(){
        return R.ok().data("item", Arrays.asList("1,2,3"));
    }
}
