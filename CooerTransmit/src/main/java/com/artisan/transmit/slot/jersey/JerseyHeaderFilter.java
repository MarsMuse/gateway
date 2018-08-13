package com.artisan.transmit.slot.jersey;

import com.artisan.transmit.slot.FlumeBootStream;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author xz man
 * @date 2018/8/6 下午3:43
 * @since v1.0
 * Jersey请求头过滤器
 *
 */
public class JerseyHeaderFilter implements ContainerRequestFilter{


    /**
     * 请求头匹配
     */
    private final String matchHeader;

    /**
     * 请求头
     */
    private final String[] headers;


    public JerseyHeaderFilter(String matchHeader, String[] headers) {
        this.matchHeader = matchHeader;
        this.headers = headers;
    }


    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        Map<String,List<String>>  requestHeaders= containerRequestContext.getHeaders();


        if(null != headers && headers.length >0){
            for(String header : headers){
                List<String> value = requestHeaders.get(header);
                if(null != value && value.size() >=1){
                    FlumeBootStream.addParameter(header, value.get(0));
                }
            }
        }
        //没有匹配的参数无需匹配
        if(null == matchHeader || matchHeader.length() ==0){
            return;
        }
        Set<String>  headerSet = requestHeaders.keySet();

        for (String headerName : headerSet){
            if(headerName.startsWith(matchHeader.toLowerCase())){
                List<String> value = requestHeaders.get(headerName);
                if(null != value && value.size() >=1){
                    FlumeBootStream.addParameter(headerName, value.get(0));
                }
            }
        }
    }
}
