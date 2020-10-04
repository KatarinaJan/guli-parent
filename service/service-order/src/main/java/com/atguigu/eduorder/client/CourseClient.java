package com.atguigu.eduorder.client;

import com.atguigu.servicebase.dto.CourseInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-29 10:25
 */
@Component
@FeignClient("service-edu")
public interface CourseClient {

    @GetMapping("/eduservice/course/front/pay/{courseId}")
    CourseInfoDto getCourseInfoDto(@PathVariable("courseId") String courseId);

}
