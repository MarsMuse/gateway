package com.artisan.transmit.slot.servlet;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Servlet;

/**
 *
 * @author xz man
 * @date 2018/7/19 下午4:42
 * 注解自动配置
 *
 */
@ConfigurationProperties(prefix = "transmit.slot.servlet",ignoreUnknownFields = true)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class})
public class ServletHeaderGrabAutoConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 需要拦截下来的头信息
     */
    private String[] headers={"userId","loginName","userSource"};

    /**
     * 需要匹配的头信息名称前缀
     */
    private String prefixHeader="artist-";

    /**
     *
     * @author xz man
     * @date 2018/7/19 下午5:04
     * 添加拦截器
     *
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                new HeaderGrabInterceptor(prefixHeader, headers)).addPathPatterns("/**");
    }


    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public String getPrefixHeader() {
        return prefixHeader;
    }

    public void setPrefixHeader(String prefixHeader) {
        this.prefixHeader = prefixHeader;
    }
}
