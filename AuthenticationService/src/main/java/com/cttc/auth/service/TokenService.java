package com.cttc.auth.service;


import com.cttc.auth.entity.UserInfo;

import java.util.Map;

/**
 *
 * @author xz man
 * @date 2018/7/20 下午5:12
 * Token服务，提供token生成，token解析，验证等服务
 *
 */
public interface TokenService {

    /**  
     *    
     * @author xz man 
     * @date 2018/7/23 下午4:25
     * @since v1.0
     * 方法描述: 构建token
     *
     */ 
    String buildAndCacheToken(UserInfo userInfo) throws Exception;
    
    
    /**  
     *    
     * @author xz man 
     * @date 2018/7/23 下午6:13
     * @since v1.0
     * 方法描述: 验证token信息
     *
     */ 
    boolean verifyToken(String token)throws Exception;

    /**  
     *    
     * @author xz man 
     * @date 2018/7/24 上午10:09  
     * @since v1.0
     * 方法描述: 抓取需要传输的头信息
     *
     */ 
    Map<String,String> grabHeaderTransmitInfo(String token)throws Exception;
}
