package com.artisan.transmit.slot.feign;

import com.artisan.transmit.slot.FlumeBootStream;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author xz man
 * @date 2018/7/19 下午5:54
 * 给Feign添加一个拦截器
 *
 */
@Configuration
@ConditionalOnClass(value = Feign.class)
@ConditionalOnProperty(value = "transmit.slot.enabled",matchIfMissing = true)
public class FeignFlumeInterceptor implements RequestInterceptor {

    /**
     *
     * @author xz man
     * @date 2018/7/19 下午5:56
     * 在Feign的请求头里面加入我们需要的信息
     *
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Map<String, String>  parameterContainer = FlumeBootStream.getParameterKeyLowerCase();
        if(parameterContainer!= null && !parameterContainer.isEmpty()){
            Set<String> keySet = parameterContainer.keySet();
            //在请求头中加入
            for(String key: keySet){
                requestTemplate.header(key,parameterContainer.get(key));
            }
        }
    }
}
