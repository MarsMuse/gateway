package com.cttc.consumer.config;


import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author xz man
 * @date 2018/7/17 下午5:31
 *
 */
@Configuration
public class FeignLogConfig {

    /**
     *
     * @author zou yao
     * @date q 下午5:33
     * 配置全局Feign log
     */
    @Bean
    Logger.Level globalFeignLog(){

        return Logger.Level.FULL;
    }
}
