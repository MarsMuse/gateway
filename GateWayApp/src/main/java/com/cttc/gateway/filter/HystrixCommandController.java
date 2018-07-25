package com.cttc.gateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.ipc.netty.http.server.HttpServerResponse;


/**
 *
 * @author zou yao
 * @date 2018/7/10 下午5:36
 * 熔断器
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "hystrix")
public class HystrixCommandController {



    @RequestMapping(value = "timeout")
    public String requestTimeout(){
        return "error";
    }
}
