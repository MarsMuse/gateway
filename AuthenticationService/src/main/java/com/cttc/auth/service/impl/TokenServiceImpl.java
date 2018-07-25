package com.cttc.auth.service.impl;

import com.cttc.auth.config.TokenBasicConfig;
import com.cttc.auth.constant.SignInConstants;
import com.cttc.auth.entity.UserInfo;
import com.cttc.auth.service.TokenService;
import com.cttc.auth.util.TokenHandler;
import com.cttc.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 *
 * @author xz man
 * @date 2018/7/20 下午5:13
 * Token服务实现
 *
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    /**
     * 默认缓存数量
     */
    private final static int DEF_CACHE_TOKEN_CAPACITY=2;

    /**
     * 生成 token 的基本配置
     */
    @Resource
    private TokenBasicConfig tokenBasicConfig;
    
    /**  
     *    
     * @author xz man 
     * @date 2018/7/23 下午4:54  
     * @since v1.0
     * 方法描述: token服务构建token
     *
     */ 
    @Override
    public String buildAndCacheToken(UserInfo userInfo)throws Exception{
        if(null == userInfo){
            log.error("构建token方法参数异常");
            throw new IllegalArgumentException("参数不可为空");
        }

        String tokenId = UUID.randomUUID().toString().replaceAll("-","");


        //获取到过期时间
        long exp = getConfigExp(userInfo.getUserSource());

        //获取到负载
        Map<String, Object>  payload =buildPayload(userInfo, tokenId, exp);

        try {
            //缓存token至数据库
            cacheToken(userInfo.getId(), tokenId, exp);
        }catch (Exception e){
            log.info("用户登录名：{}，在登录验证时出现redis失效的情况，为了保证系统可用性，暂时不缓存token数据。",
                    userInfo.getLoginName(),e);
        }
        return TokenHandler.buildToken(tokenBasicConfig.getAlgName(),
                tokenBasicConfig.getBase64Secret(),payload);
    }

    /**  
     *    
     * @author xz man 
     * @date 2018/7/24 上午11:45  
     * @since v1.0
     * 方法描述: 获取到配置过期时间
     *
     */ 
    private long getConfigExp(String userSource){

        long exp;
        if(SignInConstants.APP_SOURCE_USER.getFormName().equals(userSource)){
            exp=tokenBasicConfig.getAppExpSecond()*1000;
        }else{
            exp=tokenBasicConfig.getWebExpSecond()*1000;
        }
        return exp;
    }
    
    /**  
     *    
     * @author xz man 
     * @date 2018/7/23 下午6:13  
     * @since v1.0
     * 方法描述: 验证token信息
     *
     */ 
    @Override
    public boolean verifyToken(String token) throws Exception {

        if(null == token || "".equals(token)){
            log.info("验证token信息参数不可为空");
            return false;
        }
        boolean valid;
        Map<String,Object>  payload = TokenHandler.parseToken(token, tokenBasicConfig.getBase64Secret());
        //获取到Redis缓存的键值
        String key = tokenBasicConfig.getTokenCachePrefix()+payload.get(tokenBasicConfig.getUserId());
        //存储缓存数据
        List<String> cacheInfo = null;
        //判定redis是否异常
        boolean redisException = false;
        try{
            //获取到缓存的tokenId与exp
            cacheInfo = RedisUtil.hget(key, tokenBasicConfig.getTokenCacheIdName(),tokenBasicConfig.getRedisCacheExpName());
        }catch (Exception e){
            log.info("在读取token缓存时出现了Redis不可用的情况。",e);
            redisException = true;
        }

        //如果redis异常直接检查token中的数据
        if(redisException){
            Object localExp =payload.get(tokenBasicConfig.getLocalExpName());
            if(null != localExp){
                //降级处理，为了保证可用性，验证token内部过期时间
                long historyExp = Long.valueOf((String) localExp);
                valid = historyExp > System.currentTimeMillis();
            }else{
                valid=false;
            }
        }else{
            //验证Redis缓存的过期时间
            valid = verifyAndRefreshRedisExp(cacheInfo, payload, key);
        }
        return valid;
    }
    
    /**  
     *    
     * @author xz man 
     * @date 2018/7/24 上午10:09  
     * @since v1.0
     * 方法描述: 抓取需要传输的头信息--此处只做缓存信息抓取，不做过期校验
     *
     */ 
    @Override
    public Map<String, String> grabHeaderTransmitInfo(String token) throws Exception {
        if(null==token || "".equals(token)){
            log.info("token信息为空，无法抓取需要传输的头信息。");
            return null;
        }
        //获取到负载
        Map<String,Object> payload = TokenHandler.parseToken(token,tokenBasicConfig.getBase64Secret());
        if(null == payload ||payload.isEmpty()){
            log.info("该token信息无负载信息，无法抓取需要传输的头信息。");
            return null;
        }

        Map<String, String> headerInfo=new HashMap<>(3);
        //用户ID
        Object userIdObj = payload.get(tokenBasicConfig.getUserId());
        if(userIdObj != null){
            headerInfo.put(tokenBasicConfig.getUserId(), (String) userIdObj);
        }
        //登录名称
        Object loginNameObj =payload.get(tokenBasicConfig.getLoginName());
        if(loginNameObj != null){
            headerInfo.put(tokenBasicConfig.getLoginName(), (String) loginNameObj);
        }
        //用户来源
        Object userSourceObj =payload.get(tokenBasicConfig.getUserSource());
        if(loginNameObj != null){
            headerInfo.put(tokenBasicConfig.getUserSource(), (String) userSourceObj);
        }
        return headerInfo;
    }


    /**  
     *    
     * @author xz man 
     * @date 2018/7/23 下午6:28  
     * @since v1.0
     * 方法描述: 验证与刷新Redis过期时间
     *
     */ 
    private boolean verifyAndRefreshRedisExp(List<String> cacheInfo ,Map<String,Object>  payload,String key){
        if(null == cacheInfo || cacheInfo.size() <DEF_CACHE_TOKEN_CAPACITY){
            return false;
        }
        //缓存的tokenId
        String redisTokenId = cacheInfo.get(0);
        String expStr =cacheInfo.get(1);
        if(null ==redisTokenId || "".equals(redisTokenId) ||
                null == expStr || "".equals(expStr)){
            log.info("获取到缓存数据为空");
            return false;
        }
        //缓存的过期时间
        long redisExp = Long.valueOf(expStr);
        //判断该tokenId是否与缓存的一致，并且判定过期时间是否大于当前时间（大于则未过期）
        boolean valid= redisTokenId.equals(payload.get(tokenBasicConfig.getTokenCacheIdName())) &&
                redisExp>System.currentTimeMillis();

        if(valid){

            try {
                String userSource = (String) payload.get(tokenBasicConfig.getUserSource());
                long expCap = getConfigExp(userSource);
                //刷新的过期时间
                expCap+=System.currentTimeMillis();
                RedisUtil.hset(key, tokenBasicConfig.getRedisCacheExpName(), String.valueOf(expCap));
            }catch (Exception e){
                log.info("在刷新token缓存过期时间时出现了Redis不可用的情况，为了保证系统的可用性，暂时牺牲Redis缓存",e);
            }
        }
        return valid;

    }
    /**  
     *    
     * @author xz man 
     * @date 2018/7/23 下午5:13  
     * @since v1.0
     * 方法描述: 缓存token信息
     *
     */ 
    private void cacheToken(String userId,String tokenId, long exp)throws Exception{
        Map<String,String> cacheInfo = new HashMap<>(DEF_CACHE_TOKEN_CAPACITY);
        cacheInfo.put(tokenBasicConfig.getTokenCacheIdName(),tokenId);
        //计算过期时间
        exp+=System.currentTimeMillis();
        cacheInfo.put(tokenBasicConfig.getRedisCacheExpName(),String.valueOf(exp));
        //redis中缓存的hash键值
        String redisCacheHashKey = tokenBasicConfig.getTokenCachePrefix()+userId;
        //缓存至数据库
        RedisUtil.hmset(redisCacheHashKey,cacheInfo);

    }

    /**  
     *    
     * @author xz man 
     * @date 2018/7/23 下午4:38
     * @since v1.0
     * 方法描述: 构建token负载
     *
     */ 
    private Map<String,Object>  buildPayload(UserInfo userInfo, String tokenId ,long exp){
        Map<String,Object>  payload = new HashMap<>(4);
        //登录名
        payload.put(tokenBasicConfig.getLoginName(),userInfo.getLoginName());
        //用户ID
        payload.put(tokenBasicConfig.getUserId(),userInfo.getId());
        //token ID
        payload.put(tokenBasicConfig.getTokenCacheIdName(), tokenId);
        //用户来源 1：web端，2：app端
        payload.put(tokenBasicConfig.getUserSource(),userInfo.getUserSource());

        payload.put(tokenBasicConfig.getLocalExpName(),System.currentTimeMillis()+exp);
        return payload;
    }
}
