package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.atguigu.commonutils.ResultCode;
import com.atguigu.eduservice.constant.Course;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.query.CourseQuery;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Jan
 * @since 2020-09-11
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 保存课程基本信息
        EduCourse course = new EduCourse();
        course.setStatus(Course.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoVo, course);
        boolean resultCourseInfo = this.save(course);
        if (!resultCourseInfo) {
            throw new GuliException(ResultCode.ERROR, "课程信息保存失败");
        }
        // 保存课程详情信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(course.getId());
        boolean resultDescription = courseDescriptionService.save(courseDescription);
        if (!resultDescription) {
            throw new GuliException(ResultCode.ERROR, "课程详情信息保存失败");
        }

        return course.getId();
    }

    @Override
    public CourseInfoVo getCourseInfoById(String id) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        // 获取课程基本信息
        EduCourse courseInfo = this.getById(id);
        if (null == courseInfo) {
            throw new GuliException(ResultCode.ERROR, "课程基本数据不存在");
        }
        BeanUtils.copyProperties(courseInfo, courseInfoVo);
        // 获取描述信息
        EduCourseDescription descInfo = courseDescriptionService.getById(id);
        if (null == descInfo) {
            throw new GuliException(ResultCode.ERROR, "课程描述数据不存在");
        }
        courseInfoVo.setDescription(descInfo.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfoById(CourseInfoVo courseInfoVo) {
        // 保存课程基本信息
        EduCourse courseInfo = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, courseInfo);
        boolean resultCourseInfo = this.updateById(courseInfo);
        if (!resultCourseInfo) {
            throw new GuliException(ResultCode.ERROR, "课程基本数据保存失败");
        }
        // 保存课程详情信息
        EduCourseDescription descInfo = new EduCourseDescription();
        descInfo.setDescription(courseInfoVo.getDescription());
        descInfo.setId(courseInfo.getId());
        boolean resultDescInfo = courseDescriptionService.updateById(descInfo);
        if (!resultDescInfo) {
            throw new GuliException(ResultCode.ERROR, "课程描述信息保存失败");
        }
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public Boolean publishCourseById(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        int count = baseMapper.updateById(course);
        return count > 0;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (null == courseQuery) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return ;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public boolean removeCourseById(String id) {

        // 根据课程id删除所有小节
        int count = videoService.count(new LambdaQueryWrapper<EduVideo>().eq(EduVideo::getCourseId, id));
        if (count != 0) {
            boolean flag = videoService.removeVideoByCourseId(id);
            if (!flag) {
                throw new GuliException(ResultCode.ERROR, "删除小节失败！");
            }
        }

        // 根据课程id删除所有章节
        count = chapterService.count(new LambdaQueryWrapper<EduChapter>().eq(EduChapter::getCourseId, id));
        if (count != 0) {
            boolean flag = chapterService.removeChapterByCourseId(id);
            if (!flag) {
                throw new GuliException(ResultCode.ERROR, "删除章节失败！");
            }
        }
        // 删除课程简介信息
        count = courseDescriptionService.count(new LambdaQueryWrapper<EduCourseDescription>().eq(EduCourseDescription::getId, id));
        if (count != 0) {
            boolean flag = courseDescriptionService.removeById(id);
            if (!flag) {
                throw new GuliException(ResultCode.ERROR, "删除简介信息失败！");
            }
        }

        // 删除课程信息
        count = this.count(new LambdaQueryWrapper<EduCourse>().eq(EduCourse::getId, id));
        // 删除封面信息

        if (count != 0) {
            boolean flag = this.removeById(id);
            if (!flag) {
                throw new GuliException(ResultCode.ERROR, "删除课程信息失败！");
            }
        }
        return true;
    }
}
