package com.atguigu.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-29 10:38
 */
@EnableFeignClients // feign
@EnableDiscoveryClient // nacos
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
@MapperScan("com.atguigu.eduorder.mapper")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
