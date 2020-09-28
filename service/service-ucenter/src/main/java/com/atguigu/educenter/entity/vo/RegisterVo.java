package com.atguigu.educenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Project: guli-parent
 * @Describe: 注册对象
 * @Author: Jan
 * @Date: 2020-09-24 11:21
 */
@Data
@ApiModel(value="注册对象", description="注册对象")
public class RegisterVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;

}