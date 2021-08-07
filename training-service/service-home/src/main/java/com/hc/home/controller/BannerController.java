package com.hc.home.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.common.utils.TrainingResult;
import com.hc.home.entity.Banner;
import com.hc.home.service.BannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-03-02
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/{id}")
    @ApiOperation("根据id查询轮播图数据")
    public TrainingResult getBannerById(@PathVariable(value = "id") String id) {
        Banner banner = bannerService.getById(id);
        return TrainingResult.ok().data("banner", banner);
    }

    @GetMapping("/{page]/{limit}")
    @ApiOperation("分页查询轮播图数据")
    public TrainingResult getPageBanner(@PathVariable(value = "page") Long page,
                                        @PathVariable(value = "limit") Long limit) {
        Page<Banner> bannerPage = new Page<>(page, limit);
        bannerService.page(bannerPage, null);
        return TrainingResult.ok().data("total", bannerPage.getTotal()).data("rows", bannerPage.getRecords());
    }

    @GetMapping("/all")
    @ApiOperation("获取所有数据")
    public TrainingResult getBannerList() {
        List<Banner> bannerList;
        try {
            // 先查缓存
            Object list = redisTemplate.opsForValue().get("bannerList");
            // 查不到就查数据库，然后存缓存
            if (list == null) {
                bannerList = bannerService.list(null);
                redisTemplate.opsForValue().set("bannerList", bannerList, 5, TimeUnit.DAYS);
                System.out.println("成功加入缓存！");
                return TrainingResult.ok().data("bannerList", bannerList);
            }
            return TrainingResult.ok().data("bannerList", list);
        } catch (Exception e) {
            bannerList = bannerService.list(null);
        }
        return TrainingResult.ok().data("bannerList", bannerList);

    }

    @PostMapping("/add")
    @ApiOperation("添加轮播图")
    public TrainingResult addBanner(@RequestBody Banner banner) {
        boolean success = bannerService.save(banner);
        if (success) {
            success = redisTemplate.delete("bannerList");
        }
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误！添加失败！");
    }

    @PostMapping("/{id}")
    @ApiOperation("更新轮播图信息")
    public TrainingResult updateBanner(@PathVariable(value = "id") String bannerId,
                                       @RequestBody Banner banner) {
        banner.setId(bannerId);
        boolean success = bannerService.updateById(banner);
        // 轮播图更新成功删除缓存
        if (success) {
            success = redisTemplate.delete("bannerList");
        }
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误！更新失败！");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除轮播图")
    public TrainingResult deleteBanner(@PathVariable String id) {
        boolean success = bannerService.removeById(id);
        if (success) {
            success = redisTemplate.delete("bannerList");
        }
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误！删除失败！");
    }
}

