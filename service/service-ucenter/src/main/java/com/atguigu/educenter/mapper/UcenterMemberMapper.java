package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Jan
 * @since 2020-09-24
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

//    @Select("SELECT count(1) FROM ucenter_member WHERE Date(gmt_create) = #{day}")
    Integer countRegisterByDay(String day);

}
