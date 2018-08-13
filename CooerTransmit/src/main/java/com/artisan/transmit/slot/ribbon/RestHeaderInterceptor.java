package com.artisan.transmit.slot.ribbon;

import com.artisan.transmit.slot.FlumeBootStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author xz man
 * @date 2018/7/19 下午11:31
 * 通过拦截RestTemplate加入请求头
 *
 */
public class RestHeaderInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();

        Map<String, String> parameterContainer = FlumeBootStream.getParameterKeyLowerCase();
        if(parameterContainer!= null && !parameterContainer.isEmpty()){
            Set<String> keySet = parameterContainer.keySet();
            //在请求头中加入
            for(String key: keySet){
                headers.add(key, parameterContainer.get(key));
            }
        }

        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
