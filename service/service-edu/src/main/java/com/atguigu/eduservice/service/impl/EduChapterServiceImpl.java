package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.ResultCode;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.entity.vo.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Jan
 * @since 2020-09-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> nestedList(String courseId) {
        // 最终要得到的数据列表
        ArrayList<ChapterVo> chapterVoArrayList = new ArrayList<ChapterVo>();
        // 获取章节信息
        QueryWrapper<EduChapter> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id", courseId);
        wrapper1.orderByAsc("sort", "id");
        List<EduChapter> chapters = baseMapper.selectList(wrapper1);
        // 获取课时信息
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id", courseId);
        wrapper2.orderByAsc("sort", "id");
        List<EduVideo> videos = videoService.list(wrapper2);
        // 填充章节vo数据
        chapters.forEach(chapter -> {
            // 创建章节vo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoArrayList.add(chapterVo);
            // 填充课时vo数据
            ArrayList<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo video : videos) {
                if (chapter.getId().equals(video.getChapterId())) {
                    // 创建课时vo数据
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
        });
        return chapterVoArrayList;
    }

    @Override
    public boolean removeChapterById(String id) {
        // 根据id查询是否存在视频，如果有则提示用户尚有子节点
        if (videoService.getCountByChapterId(id)) {
            throw new GuliException(ResultCode.ERROR, "该分章节下存在视频课程，请先删除视频课程");
        }
        int result = baseMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public boolean removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        int count = baseMapper.delete(queryWrapper);
        return count > 0;
    }
}
