package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.commonutils.ResultCode;
import com.atguigu.eduservice.entity.vo.SubjectNestedVo;
import com.atguigu.eduservice.entity.vo.SubjectVo;
import com.atguigu.eduservice.listen.SubjectExcelListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * 添加课程分类
 * poi读取excel内容
 * </p>
 *
 * @author Jan
 * @since 2020-09-10
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void importSubjectData(MultipartFile file, EduSubjectService subjectService) {
        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();
            // 读取excel文件
            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new GuliException(ResultCode.ERROR, "添加课程分类失败");
        }
    }

    @Override
    public List<SubjectNestedVo> nestedList() {

        // 最终要得到的数据列表
        ArrayList<SubjectNestedVo> subjectNestedVosArrayList = new ArrayList<>();

        // 获取一级分类数据记录
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        queryWrapper.orderByAsc("sort", "id");
        List<EduSubject> subjects = baseMapper.selectList(queryWrapper);

        // 获取二级分类数据记录
        queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("parent_id", 0);
        queryWrapper.orderByAsc("sort", "id");
        List<EduSubject> subSubjects = baseMapper.selectList(queryWrapper);

        // 填充一级分类VO数据
        for (EduSubject subject : subjects) {
            // 创建一级类别vo对象
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
            BeanUtils.copyProperties(subject, subjectNestedVo);
            subjectNestedVosArrayList.add(subjectNestedVo);
            // 创建二级分类VO数据
            ArrayList<SubjectVo> subjectVoArrayList = new ArrayList<>();
            for (EduSubject subSubject : subSubjects) {
                if (subject.getId().equals(subSubject.getParentId())) {
                    // 创建二级类别VO对象
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(subSubject, subjectVo);
                    subjectVoArrayList.add(subjectVo);
                }
            }
            subjectNestedVo.setChildren(subjectVoArrayList);
        }
        return subjectNestedVosArrayList;
    }

}
