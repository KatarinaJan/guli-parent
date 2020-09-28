package com.atguigu.eduservice.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Project: guli-parent
 * @Describe: 用户课程详情界面展示对象
 * @Author: Jan
 * @Date: 2020-09-27 17:45
 */
@ApiModel(value = "课程信息", description = "网站课程详情页需要的相关字段")
@Data
public class CourseWebVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "销售数量")
    private Long buyCount;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "课程简介")
    private String description;

    @ApiModelProperty(value = "所属分类id：一级")
    private String subjectLevelOneId;

    @ApiModelProperty(value = "所属分类：一级")
    private String subjectLevelOne;

    @ApiModelProperty(value = "所属分类id：二级")
    private String subjectLevelTwoId;

    @ApiModelProperty(value = "所属分类：二级")
    private String subjectLevelTwo;

    @ApiModelProperty(value = "讲师ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String teacherId;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "讲师简介")
    private String intro;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;
}
