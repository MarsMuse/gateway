package com.cttc.auth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author xz man
 * @date 2018/7/16 下午3:04
 * Token基本配置
 *
 */
@Data
@Component
public class TokenBasicConfig {

    /**
     * 缓存token前缀
     */
    @Value("${token.tokenCachePrefix:TOKEN-CACHE}")
    private String tokenCachePrefix;

    /**
     * base64秘钥
     */
    @Value("${token.base64Secret}")
    private String base64Secret;

    /**
     * web端登陆过期时间
     */
    @Value("${token.webExpSecond:1800}")
    private int webExpSecond;

    /**
     * APP端登陆过期时间
     */
    @Value("${token.appExpSecond:43200}")
    private int appExpSecond;

    /**
     * 签名算法名称
     */
    @Value("${token.algName:HS256}")
    private String algName;

    /**
     * token 中的登录名 payload
     */
    @Value("${token.loginName:loginName}")
    private String loginName ;


    /**
     * token id payload
     */
    @Value("${token.tokenCacheIdName:tokenId}")
    private String tokenCacheIdName;

    /**
     * 用户ID
     */
    @Value("${token.userId:userId}")
    private String userId;

    /**
     * 用户源
     */
    @Value("${token.userSource:userSource}")
    private String userSource;

    /**
     * redis 缓存时间名
     */
    @Value("${token.redisCacheExpName:exp}")
    private String redisCacheExpName;

    /**
     * 前端缓存token的名称
     */
    @Value("${token.frontEndCacheAuthHeaderName:Authorization}")
    private String frontEndCacheAuthHeaderName;

    /**
     * 令牌本身的exp名称--避免使用exp，这样jwt不会校验过期时间
     */
    @Value("${token.localExpName:localExp}")
    private String localExpName;





}
