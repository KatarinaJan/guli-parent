package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-29 10:21
 */
@Data
public class MemberDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员id")
    private String memberId;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "会员手机")
    private String mobile;

    @ApiModelProperty(value = "会员头像")
    private String avatar;
}
