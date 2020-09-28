package com.atguigu.eduservice.controller;


import com.alibaba.excel.util.StringUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.query.CourseQuery;
import com.atguigu.eduservice.entity.query.CourseQueryDto;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Jan
 * @since 2020-09-11
 */
@Api(description = "课程管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "新增课程")
    @PostMapping("addCourseInfo")
    public R saveCourseInfo(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo){
        String courseId = courseService.saveCourseInfo(courseInfoVo);
        if (!StringUtils.isEmpty(courseId)) {
            return R.ok().data("courseId", courseId);
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("info/{id}")
    public R geCourseInfotById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        CourseInfoVo courseInfo = courseService.getCourseInfoById(id);
        return R.ok().data("item", courseInfo);
    }

    @ApiOperation(value = "更新课程")
    @PutMapping("info/{id}")
    public R updateCourseInfoById(
            @ApiParam(name = "CourseInfoVo", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo,
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        courseService.updateCourseInfoById(courseInfoVo);
        return R.ok().message("保存成功");
    }

    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("publish/{id}")
    public R getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoById(id);
        return R.ok().data("item", coursePublishVo);
    }

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publish/{id}")
    public R publishCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        Boolean flag = courseService.publishCourseById(id);
        if (!flag) {
            return R.error().message("发布课程失败！");
        }
        return R.ok();
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            CourseQuery courseQuery) {
        Page<EduCourse> pageParam = new Page<>(page, limit);

        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();

        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        boolean result = courseService.removeCourseById(id);
        if (result) {
            return R.ok().message("删除成功！");
        } else {
            return R.error().message("删除失败！");
        }
    }

    @ApiOperation(value = "用户界面-根据条件分页查询")
    @PostMapping("/front/{page}/{limit}")
    public R pageListWeb(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQueryDto courseQuery) {
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(coursePage, courseQuery);
        if (null == map) {
            return R.error().message("没有查到任何信息");
        }
        return R.ok().data(map).message("获取数据成功！");
    }

    @ApiOperation(value = "用户界面-根据id查询课程详情")
    @GetMapping("/front/{courseId}")
    public R selectWebInfoById(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId) {
        // 查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectWebInfoById(courseId);
        // 查询当前课程章节信息
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return R.ok().data("courseWebVo", courseWebVo).data("chapterVoList", chapterVoList);
    }

}

