package com.artisan.transmit.slot;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author xz man
 * @date 2018/7/19 下午4:06
 * 水槽导流器
 *
 */
public class FlumeBootStream {

    /**
     * 线程本地存储
     */
    private static final ThreadLocal<Map<String,String>>  LOCAL_STORAGE = new ThreadLocal<>();

    /**
     * 缓存容器默认容量
     */
    private static final int DEF_CONTAINER_CAPACITY = 8;


    /**
     *
     * @author xz man
     * @date 2018/7/20 上午10:43
     * 获取到线程本地存储的容器--若未初始化，则需要初始化容器
     *
     */
    private static Map<String,String> getContainer(){

        //初始化容器对象
        Map<String,String>  parameterContainer = LOCAL_STORAGE.get();
        if(parameterContainer == null){
            parameterContainer = new HashMap<>(DEF_CONTAINER_CAPACITY);
            LOCAL_STORAGE.set(parameterContainer);
        }
        
        return parameterContainer;
    }

    /**
     *
     * @author xz man
     * @date 2018/7/19 下午4:11
     * 添加参数
     *
     */
    public static void addParameter(String key, String value){
        if(null == key || "".equals(key) || null == value || "".equals(value)){
            return;
        }

        Map<String,String>  parameterContainer = getContainer();
        parameterContainer.put(key.toLowerCase(), value);
    }

    /**
     *
     * @author xz man
     * @date 2018/7/19 下午4:11
     * 添加参数
     *
     */
    public static void addParameter(Map<String ,String>  headers){
        if(headers == null || headers.isEmpty()){
            return;
        }
        Set<String>  keySet = headers.keySet();
        Map<String,String>  parameterContainer = getContainer();
        for(String key: keySet){
            String value = headers.get(key);
            parameterContainer.put(key.toLowerCase(), value);
        }
    }

    /**
     *
     * @author xz man
     * @date 2018/7/19 下午4:19
     * 获取到参数
     *
     */
    public static String getParameter(String key){
        if(null == key || "".equals(key)){
            return null;
        }

        Map<String,String>  parameterContainer = getContainer();
        return parameterContainer.get(key.toLowerCase());
    }

    /**
     *
     * @author xz man
     * @date 2018/7/19 下午4:19
     * 获取到参数
     *
     */
    public static Map<String,String> getParameterKeyLowerCase(){

        return getContainer();
    }

    /**
     *
     * @author xz man
     * @date 2018/7/19 下午4:29
     * 清除资源
     *
     */
    public static void clear(){

        LOCAL_STORAGE.remove();
    }
}
