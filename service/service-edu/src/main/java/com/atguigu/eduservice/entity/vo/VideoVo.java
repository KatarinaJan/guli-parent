package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-14 17:40
 */
@ApiModel(value = "课时信息")
@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "视频ID")
    private String id;

    @ApiModelProperty(value = "节点名称")
    private String title;

    @ApiModelProperty(value = "是否可以试听：0默认 1免费")
    private Boolean free;

    @ApiModelProperty(value = "云服务器上存储的视频文件名称")
    private String videoOriginalName;

}
