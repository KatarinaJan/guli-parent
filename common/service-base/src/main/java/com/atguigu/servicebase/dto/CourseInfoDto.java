package com.atguigu.servicebase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-29 11:58
 */
@Data
public class CourseInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseTitle;

    @ApiModelProperty(value = "课程封面")
    private String courseCover;

    @ApiModelProperty(value = "讲师名称")
    private String teacherName;

    @ApiModelProperty(value = "订单金额（分）")
    private BigDecimal totalFee;
}
