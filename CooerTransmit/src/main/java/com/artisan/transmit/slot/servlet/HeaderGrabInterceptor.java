package com.artisan.transmit.slot.servlet;

import com.artisan.transmit.slot.FlumeBootStream;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 *
 * @author xz man
 * @date 2018/7/19 下午4:27
 * 头信息抓取拦截器
 *
 */
public class HeaderGrabInterceptor implements HandlerInterceptor {

    /**
     * 请求头匹配
     */
    private final String matchHeader;

    /**
     * 请求头
     */
    private final String[] headers;


    public HeaderGrabInterceptor(String matchHeader, String[] headers) {
        this.matchHeader = matchHeader;
        this.headers = headers;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if(null != headers && headers.length >0){
            for(String header : headers){
                String value = request.getHeader(header);
                if(null != value && value.length() >0){
                    FlumeBootStream.addParameter(header, value);
                }
            }
        }
        //没有匹配的参数无需匹配
        if(null == matchHeader || matchHeader.length() ==0){
            return true;
        }
        //匹配前缀
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String currentHeaderName = headerNames.nextElement();
            if(currentHeaderName.startsWith(matchHeader.toLowerCase())){
                String value = request.getHeader(currentHeaderName);
                if(null != value && value.length() >0){
                    FlumeBootStream.addParameter(currentHeaderName, value);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)  {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //清除资源
        FlumeBootStream.clear();
    }
}
