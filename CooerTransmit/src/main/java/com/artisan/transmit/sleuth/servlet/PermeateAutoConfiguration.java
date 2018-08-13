package com.artisan.transmit.sleuth.servlet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author xz man
 * @date 2018/7/18 下午3:56
 * 通过Sleuth渗透传输自动化配置
 * 由于sleuth2.0与之前版本差异过于大，参数携带方式被修改，故暂时停止支持
 *
 */
@Configuration
@ConditionalOnProperty(value = "transmit.sleuth.servlet.headers")
public class PermeateAutoConfiguration {

    /**
     * 获取到配置文件中需要的头文件
     */
    @Value("${transmit.sleuth.servlet.headers}")
    private String[] headers;



}
