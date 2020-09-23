package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-14 17:38
 */
@ApiModel(value = "章节信息")
@Data
public class ChapterVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private List<VideoVo> children = new ArrayList<>();
}
