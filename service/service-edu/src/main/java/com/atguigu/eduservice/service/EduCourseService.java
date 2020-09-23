package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.query.CourseQuery;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Jan
 * @since 2020-09-11
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 保存课程和课程详情信息
     * @param courseInfoVo 课程信息
     * @return 新生成的课程id
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 回显更新课程信息
     */
    CourseInfoVo getCourseInfoById(String id);

    /**
     * 更新课程信息
     */
    void updateCourseInfoById(CourseInfoVo courseInfoVo);

    /**
     * 按id获取发布信息
     * @param id 课程id
     * @return 发布信息
     */
    CoursePublishVo getCoursePublishVoById(String id);

    /**
     * 按课程id发布信息
     * @param id 课程id
     */
    Boolean publishCourseById(String id);

    /**
     * 按条件查询分页
     * @param pageParam 分页参数
     * @param courseQuery 条件
     */
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    /**
     * 按课程id删除课程
     * 首先删除video记录，
     * 然后删除chapter记录，
     * 最后删除Course记录
     * @param id 课程id
     */
    boolean  removeCourseById(String id);
}
