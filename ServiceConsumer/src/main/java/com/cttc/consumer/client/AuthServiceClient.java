package com.cttc.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author zou yao
 * @date 2018/7/17 下午4:19
 * AUTH-SERVICE 统一的客户端
 *
 */
@FeignClient(value = "AUTH-SERVICE")
public interface AuthServiceClient {
    /**
     *
     * @author xz man
     * @date 2018/7/17 下午5:34
     * 获取到主机配置
     *
     */
    @RequestMapping(value = "/envInfo/showHost")
    String showHost();

}
