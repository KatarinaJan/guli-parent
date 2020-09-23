package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Jan
 * @since 2020-09-11
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean getCountByChapterId(String chapterId);

    void saveVideoInfo(VideoInfoVo videoInfoVo);

    VideoInfoVo getVideoInfoById(String id);

    void updateVideoInfoById(VideoInfoVo videoInfoVo);

    boolean removeVideoById(String id);

    boolean removeVideoByCourseId(String courseId);
}
