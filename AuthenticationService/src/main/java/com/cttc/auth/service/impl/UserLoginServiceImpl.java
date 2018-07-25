package com.cttc.auth.service.impl;

import com.cttc.auth.entity.UserInfo;
import com.cttc.auth.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**  
 *    
 * @author xz man 
 * @date 2018/7/23 下午4:21
 * @since v1.0
 * 用户登录服务
 */
@Slf4j
@Service
public class UserLoginServiceImpl implements UserLoginService {


    @Override
    public UserInfo webUserLogin(String loginName, String password) throws Exception {

        UserInfo userInfo = null;

        if("man".equals(loginName) && "abc123456".equals(password)){
            userInfo = new UserInfo();
            userInfo.setId(UUID.randomUUID().toString().replaceAll("-",""));
            userInfo.setLoginName(loginName);
        }

        return userInfo;
    }

    @Override
    public UserInfo appUserLogin(String loginName, String password) throws Exception {
        return new UserInfo();
    }
}
