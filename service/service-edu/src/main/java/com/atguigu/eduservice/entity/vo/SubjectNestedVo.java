package com.atguigu.eduservice.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-10 14:42
 */
@Data
public class SubjectNestedVo {

    private String id;

    private String title;

    private List<SubjectVo> children = new ArrayList<>();

}
