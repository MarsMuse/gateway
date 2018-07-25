package com.cttc.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.cttc.auth.config.TokenBasicConfig;
import com.cttc.auth.entity.ReactiveMessage;
import com.cttc.auth.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author xz man
 * @date 2018/7/12 上午10:45
 *
 */
@Slf4j
@Component
public class TokenCheckGatewayFilterFactory implements GatewayFilterFactory {

    /**
     * token 基本配置信息
     */
    @Resource
    private TokenBasicConfig tokenBasicConfig;

    /**
     * token 服务
     */
    @Resource
    private TokenService tokenService;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            //获取到token
            String token = exchange.getRequest().getHeaders().getFirst(tokenBasicConfig.getFrontEndCacheAuthHeaderName());
            if(token == null || "".equals(token)){
                log.info("该请求未获取到用户token");
                return tokenVerifyError(exchange);
            }

            boolean nextFlag;
            try{
                //处理Token信息传递逻辑
                nextFlag = tokenService.verifyToken(token);
            }catch (Exception e){
                nextFlag =false;
                log.info("验证token 出现异常。",e);
            }

            if(nextFlag){
                Map<String,String>  headerMap =null;
                try {
                    //获取到Map
                    headerMap = tokenService.grabHeaderTransmitInfo(token);
                } catch (Exception e) {
                    log.info("在抓取header信息时出现异常。", e);
                }
                //无需传递，直接过滤
                if(null == headerMap || headerMap.isEmpty()){
                    return chain.filter(exchange);
                }else{
                    return chain.filter(buildRequestAddHeader(headerMap, exchange));
                }
            }else{
                log.info("token 验证失败");
                return tokenVerifyError(exchange);
            }

        };
    }

    /**  
     *    
     * @author xz man 
     * @date 2018/7/24 上午11:35  
     * @since v1.0
     * 方法描述: 构建新Request对象，用以添加header
     *
     */ 
    private ServerWebExchange buildRequestAddHeader(final Map<String,String> finalHeaderMap, ServerWebExchange exchange){
        //创建新的request
        ServerHttpRequest request = exchange.getRequest().mutate().headers(httpHeaders -> {
            Set<String> keySet = finalHeaderMap.keySet();
            //循环添加key
            for(String key: keySet){
                String value =finalHeaderMap.get(key);
                if(null == value || "".equals(value)){
                    continue;
                }
                httpHeaders.add(key,value);
            }
        }).build();

        return exchange.mutate().request(request).build();
    }


    /**
     *
     * @author xz man
     * @date 2018/7/16 下午5:33
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * token 验证失败处理
     *
     */
    private Mono<Void> tokenVerifyError(ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        //获取到头信息
        HttpHeaders headers = response.getHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE,"application/json; charset=UTF-8");
        headers.add(HttpHeaders.CACHE_CONTROL,"no-store, no-cache");

        ReactiveMessage rm =new ReactiveMessage(300,"用户未登录或者登录超时...");

        DataBuffer db =response.bufferFactory().wrap(JSON.toJSONString(rm).getBytes());
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        return response.writeWith(Mono.just(db));
    }

    @Override
    public Object newConfig() {
        return new Object();
    }
}
