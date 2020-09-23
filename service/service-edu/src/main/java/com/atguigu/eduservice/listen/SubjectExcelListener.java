package com.atguigu.eduservice.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.commonutils.ResultCode;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.eduservice.service.impl.EduSubjectServiceImpl;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 不被spring管理
 *
 * @Project: guli-parent
 * @Describe: 读取Excel监听器
 * @Author: Jan
 * @Date: 2020-09-10 11:48
 */
@AllArgsConstructor
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public EduSubjectService subjectService;

    // 无参构造
//    public SubjectExcelListener(){}

    // 有参构造  创建有参数构造，传递subjectService用于操作数据库
//    public SubjectExcelListener(EduSubjectService subjectService) {
//        this.subjectService = subjectService;
//    }

    // 一行一行去读取excle内容
    @Override
    public void invoke(ExcelSubjectData user, AnalysisContext analysisContext) {
        if (null == user) {
            throw new GuliException(ResultCode.ERROR, "没有读取到数据，添加失败！");
        }
        System.out.println(user);
        // 添加一级分类
        EduSubject existOneSubject = this.existOneSubject(subjectService, user.getOneSubjectName());
        if (null == existOneSubject) {
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(user.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }

        // 添加二级分类
        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, user.getTwoSubjectName(), pid);
        if (null == existTwoSubject) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(user.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }

    // 读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息：" + headMap);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    // 判断一级分类是否重复
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        return subjectService.getOne(wrapper);
    }

    // 判断二级分类是否重复
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        return subjectService.getOne(wrapper);
    }
}
