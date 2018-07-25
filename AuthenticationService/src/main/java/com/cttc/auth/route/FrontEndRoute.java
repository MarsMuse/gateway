package com.cttc.auth.route;

import com.cttc.auth.handler.SignInHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.Resource;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;


/**
 *
 * @author xz man
 * @date 2018/7/23 上午10:58
 * 前端路由
 *
 */
@Configuration
public class FrontEndRoute {

    /**
     * token 处理器
     */
    @Resource
    private SignInHandler signInHandler;

    /**
     *
     * @author xz man
     * @date 2018/7/23 下午2:27
     * @since v1.0
     * 方法描述: 登录路由
     *
     */
    @Bean
    public RouterFunction<ServerResponse>  signInRouter(){


        return route(POST("/user/login/web"),request->signInHandler.loginWeb(request)).
                andRoute(POST("/user/login/app"),request->signInHandler.loginApp(request));
    }
}
