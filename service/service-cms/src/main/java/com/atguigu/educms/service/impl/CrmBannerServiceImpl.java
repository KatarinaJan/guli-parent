package com.atguigu.educms.service.impl;

import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Jan
 * @since 2020-09-23
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public void pageBanner(Page<CrmBanner> pageParam, Wrapper<CrmBanner> queryWrapper) {
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public CrmBanner getBannerById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    @CachePut(value = "banner")
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    @Override
    @Cacheable(value = "banner")
    public void updateBannerById(CrmBanner banner) {
        baseMapper.updateById(banner);
    }

    @Override
    @CacheEvict(value = "banner", allEntries = true)
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }

    @Override
    @Cacheable(value = "banner", key = "'selectIndexList'")
    public List<CrmBanner> selectIndexList() {
        return baseMapper.selectList(new QueryWrapper<CrmBanner>().orderByAsc("sort"));
    }
}
