package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Jan
 * @since 2020-09-11
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo selectCoursePublishVoById(String id);

    /**
     * 根据课程id查询用户界面课程详情
     * @param courseId 课程id
     * @return 课程详情
     */
    CourseWebVo selectWebInfoById(String courseId);
}
