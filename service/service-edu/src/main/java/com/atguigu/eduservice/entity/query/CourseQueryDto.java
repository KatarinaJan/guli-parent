package com.atguigu.eduservice.entity.query;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Project: guli-parent
 * @Describe: 前端页面课程查询条件对象
 * @Author: Jan
 * @Date: 2020-09-27 13:28
 */
@ApiModel(value = "课程查询对象", description = "前端页面课程查询条件对象")
@Data
public class CourseQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "课程专业ID")
    private String subjectId;

    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;

    @ApiModelProperty(value = "销售排序")
    private String buyCountSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtModifiedSort;

    @ApiModelProperty(value = "价格排序")
    private String priceSort;

}
