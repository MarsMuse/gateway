package com.artisan.transmit.slot.ribbon;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;

/**
 *
 * @author xz man
 * @date 2018/7/19 下午10:17
 * RestTemplate 拦截器自动配置
 *
 */
@Configuration
@ConditionalOnBean(RestTemplate.class)
@ConditionalOnProperty(value = "transmit.slot.enabled",matchIfMissing = true)
public class RestHeaderAutoConfiguration {

    @Resource
    private RestTemplate restTemplate;

    @Bean
    public RestHeaderInterceptor restHeaderInterceptor(){

        RestHeaderInterceptor restHeaderInterceptor = new RestHeaderInterceptor();

        restTemplate.setInterceptors(Collections.singletonList(restHeaderInterceptor));

        return restHeaderInterceptor;
    }
}
