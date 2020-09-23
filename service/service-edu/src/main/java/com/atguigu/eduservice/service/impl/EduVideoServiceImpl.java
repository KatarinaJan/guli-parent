package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.atguigu.commonutils.ResultCode;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoInfoVo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Jan
 * @since 2020-09-11
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        Integer count = baseMapper.selectCount(wrapper);
        return null != count && count > 0;
    }

    @Override
    public void saveVideoInfo(VideoInfoVo videoInfoVo) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoVo, video);
        video.setIsFree(videoInfoVo.getFree() ? 1 : 0);
        boolean result = this.save(video);
        if (!result) {
            throw new GuliException(ResultCode.ERROR, "课时信息保存失败！");
        }
    }

    @Override
    public VideoInfoVo getVideoInfoById(String id) {
        // 从video表中取数据
        EduVideo video = this.getById(id);
        if (null == video) {
            throw new GuliException(ResultCode.ERROR, "数据不存在！");
        }
        // 创建videoInfoVo对象
        VideoInfoVo videoInfoVo = new VideoInfoVo();
        BeanUtils.copyProperties(video, videoInfoVo);
        videoInfoVo.setFree(video.getIsFree() == 1);
        return videoInfoVo;
    }

    @Override
    public void updateVideoInfoById(VideoInfoVo videoInfoVo) {
        // 保存课时基本信息
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoVo, video);
        video.setIsFree(videoInfoVo.getFree() ? 1 : 0);
        boolean result = this.updateById(video);
        if (!result) {
            throw new GuliException(ResultCode.ERROR, "课时信息保存失败！");
        }
    }

    @Override
    public boolean removeVideoById(String id) {
        // 删除视频资源 TODO
        EduVideo video = baseMapper.selectById(id);
        if (null == video) {
            throw new GuliException(ResultCode.ERROR, "没有找到对应的课时");
        }
        String videoSourceId = video.getVideoSourceId();
        if (null == videoSourceId){
            throw new GuliException(ResultCode.ERROR, "没有找到对应的视频ID");
        }
        vodClient.removeVideo(videoSourceId);
        int result = baseMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public boolean removeVideoByCourseId(String courseId) {
        // 根据课程id查询所有视频列表
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<EduVideo> videoList = baseMapper.selectList(queryWrapper);
        // 得到所有视频列表的云端原始视频id
        ArrayList<String> videoSourceIdList = new ArrayList<>();
        for (EduVideo video : videoList) {
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoSourceIdList.add(videoSourceId);
            }
        }
        // 调用vod服务删除远程视频
        if (videoSourceIdList.size() > 0) {
            vodClient.removeVideoList(videoSourceIdList);
        }
        // 删除数据库中记录
        int count = baseMapper.delete(queryWrapper);
        return count > 0;
    }
}
