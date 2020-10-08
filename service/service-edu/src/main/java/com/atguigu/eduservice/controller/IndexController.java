package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-23 14:16
 */
@Api(description = "前端查询课程名师接口")
@RestController
@RequestMapping("/eduservice/index")
//@CrossOrigin
public class IndexController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "用户界面-查询前8条热门课程，前4条名师")
    @GetMapping
    public R index() {
        LambdaQueryWrapper<EduCourse> courseWrapper = new LambdaQueryWrapper<>();
        List<EduCourse> courseList = courseService.list(courseWrapper.orderByDesc(EduCourse::getViewCount).last("limit 8"));
        LambdaQueryWrapper<EduTeacher> teacherWrapper = new LambdaQueryWrapper<>();
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper.orderByAsc(EduTeacher::getSort).last("limit 4"));
        return R.ok().data("courseList", courseList).data("teacherList", teacherList).message("数据获取成功！");
    }

}
