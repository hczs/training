package com.hc.qa.client;


import com.hc.common.utils.TrainingResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户中心微服务远程调用
 * @author hczs8
 */
@FeignClient(name = "service-ucenter")
@Component
public interface UcenterClient {


    /**
     * 通过StaffId来获取用户信息
     * @param staffId staffId
     * @return TrainingResult
     */
    @GetMapping("/info/{staffId}")
    TrainingResult getUserInfo(@PathVariable(value = "staffId") String staffId);
}
