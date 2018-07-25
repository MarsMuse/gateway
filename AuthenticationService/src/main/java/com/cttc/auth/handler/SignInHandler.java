package com.cttc.auth.handler;

import com.cttc.auth.constant.SignInConstants;
import com.cttc.auth.entity.ReactiveMessage;
import com.cttc.auth.entity.UserInfo;
import com.cttc.auth.service.TokenService;
import com.cttc.auth.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

/**
 *
 * @author xz man
 * @date 2018/7/23 上午10:58
 * 登录  处理器
 *
 */
@Slf4j
@Component
public class SignInHandler {

    /**
     * token 服务
     */
    @Resource
    private TokenService tokenService;

    /**
     * 用户登录服务
     */
    @Resource
    private UserLoginService userLoginService;

    /**
     *
     * @author xz man
     * @date 2018/7/23 下午2:11
     * web 端登录
     *
     */
    public Mono<ServerResponse>  loginWeb(ServerRequest request ){

        Mono<Map>  requestData = request.bodyToMono(HashMap.class);
        return requestData.flatMap(this::loginWebLogic).flatMap(rm-> status(HttpStatus.OK).
                contentType(MediaType.APPLICATION_JSON).
                body(Mono.just(rm),ReactiveMessage.class));
    }
    
    /**  
     *    
     * @author xz man 
     * @date 2018/7/23 下午4:02  
     * @since v1.0
     * 方法描述: web 端登录逻辑处理部分
     *
     */ 
    private Mono<ReactiveMessage> loginWebLogic(Map param){
        if(null ==param|| param.isEmpty()){
            log.info("调用登录逻辑处理参数不合法");
            return Mono.just(ReactiveMessage.operateError());
        }
        //登录名
        Object loginNameObj = param.get(SignInConstants.WEB_LOGIN_NAME.getFormName());
        //密码
        Object passwordObj = param.get(SignInConstants.WEB_PASSWORD.getFormName());

        if(null == loginNameObj || null ==passwordObj){
            log.info("登录名或者密码为空，登录失败");
            return Mono.just(ReactiveMessage.operateError());
        }
        String loginName = (String) loginNameObj;
        String password = (String) passwordObj;
        ReactiveMessage rm;
        try {

            //验证用户名密码并且返回用户基本信息
            UserInfo currentUser = userLoginService.webUserLogin(loginName, password);
            if(currentUser == null){
                log.info("登录名：{}，登录验证失败。", loginName);
                rm =ReactiveMessage.operateError();
            }else{
                String token = tokenService.buildAndCacheToken(currentUser);
                rm=ReactiveMessage.operateSuccess();
                log.info("登录名：{}，登录验证成功。", loginName);
                rm.setData(token);
            }
        }catch (Exception e){
            log.error("用户登录名：{}，在登录时出现异常情况。", loginName, e);
            rm =ReactiveMessage.operateError();
        }
        return Mono.just(rm);
    }




    /**
     *
     * @author xz man
     * @date 2018/7/23 下午2:18
     * APP 端登录
     *
     */
    public Mono<ServerResponse>  loginApp(ServerRequest request ){


        return status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(Mono.just(new ReactiveMessage(200,"test")),ReactiveMessage.class);
    }


}
