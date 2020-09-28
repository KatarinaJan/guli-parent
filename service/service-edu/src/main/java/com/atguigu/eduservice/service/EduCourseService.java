package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.query.CourseQuery;
import com.atguigu.eduservice.entity.query.CourseQueryDto;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseWebVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据讲师id查询课程
     * 在teacher控制器中使用
     * @param teacherId 讲师id
     * @return 课程列表
     */
    List<EduCourse> selectByTeacherId(String teacherId);

    /**
     * 根据前端条件查询课程信息列表
     * @param pageParam 分页条件
     * @param courseQuery 课程条件
     * @return 课程列表
     */
    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryDto courseQuery);

    /**
     * 根据课程id查询用户界面课程详情
     * @param id 课程id
     * @return 用户界面课程详情
     */
    CourseWebVo selectWebInfoById(String id);

    /**
     * 只要点入课程详情就需要更新课程浏览数
     * @param id 课程id
     */
    void updatePageViewCount(String id);
}
