package com.cttc.auth.service;

import com.cttc.auth.entity.UserInfo;

/**  
 *    
 * @author xz man 
 * @date 2018/7/23 下午4:21
 * @since v1.0
 * 用户登录服务
 * 
 */ 
public interface UserLoginService {
    
    /**  
     *    
     * @author xz man 
     * @date 2018/7/23 下午4:20  
     * @since v1.0
     * 方法描述: web端用户登录
     *
     */ 
    UserInfo webUserLogin(String loginName, String password)  throws Exception;

    /**  
     *    
     * @author xz man 
     * @date 2018/7/23 下午4:20  
     * @since v1.0
     * 方法描述: app 端用户登录
     *
     */ 
    UserInfo appUserLogin(String loginName, String password)  throws Exception;


}
