package com.artisan.transmit.sleuth.servlet;





import brave.Span;
import brave.Tracer;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.sleuth.instrument.web.SleuthWebProperties;
import org.springframework.web.filter.GenericFilterBean;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 *
 * @author xz man
 * @date 2018/7/18 下午4:14
 * 渗透传输过滤器--在此填装数据
 *
 */
public class PermeateInfoFilter extends GenericFilterBean {

    /**
     * 获取需要跳转匹配
     */
    private Pattern skipPattern = Pattern.compile(SleuthWebProperties.DEFAULT_SKIP_PATTERN);

    /**
     * 头信息
     */
    private String[] headers;

    /**
     * 追踪器对象
     */
    private Tracer tracer;

    public PermeateInfoFilter(String[] headers, Tracer tracer) {
        this.headers = headers;
        this.tracer = tracer;
    }

    /**
     *
     * @author xz man
     * @date 2018/7/18 下午4:17
     *
     *
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("Filter just supports HTTP requests");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        boolean skip = skipPattern.matcher(httpRequest.getRequestURI()).matches();
        if (!skip) {
            Span span =tracer.currentSpan();
            for (String head : headers){
                String value = httpRequest.getHeader(head);
                if(StringUtils.isNotEmpty(value)){
                    span.tag(head,value);

                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
