package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Jan
 * @since 2020-09-23
 */
public interface CrmBannerService extends IService<CrmBanner> {

    void pageBanner(Page<CrmBanner> pageParam, Wrapper<CrmBanner> queryWrapper);

    CrmBanner getBannerById(String id);

    void saveBanner(CrmBanner banner);

    void updateBannerById(CrmBanner banner);

    void removeBannerById(String id);

    List<CrmBanner>  selectIndexList();

}
