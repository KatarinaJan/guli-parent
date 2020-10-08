package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-07 16:40
 */
@Api(description = "登录管理")
@RestController
//@CrossOrigin // 跨域请求
@RequestMapping("/eduservice/user")
public class EduLoginController {

    // login
    @PostMapping("/login")
    public R login() {
        return R.ok().data("token","admin");
    }

    // info
    @GetMapping("/info")
    public R info(@RequestParam String token) {
        return R.ok().data("roles","[admin]").data("name", "admin").data("avatar","https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2990258295,3888000098&fm=26&gp=0.jpg");
    }

    @PostMapping("/logout")
    public R logout() {
        return R.ok();
    }

}
