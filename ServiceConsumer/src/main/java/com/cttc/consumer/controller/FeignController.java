package com.cttc.consumer.controller;

import com.cttc.consumer.client.AuthServiceClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class FeignController {

    @Resource
    private AuthServiceClient authServiceClient;

    @RequestMapping(value = "hello")
    public String showHost(){

        return authServiceClient.showHost();
    }


}
