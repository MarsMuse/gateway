package com.artisan.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "AUTH-SL")
public interface AuthSl {


    @RequestMapping(value = "/envInfo/showHome")
    String show();
}
