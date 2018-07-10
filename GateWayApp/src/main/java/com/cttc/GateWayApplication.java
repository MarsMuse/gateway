package com.cttc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 * @author xz man
 * @date 2018/7/10 下午3:50
 * 网关应用
 *
 */
@EnableEurekaClient
@SpringBootApplication
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class);
    }
}
