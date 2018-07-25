package com.artisan.transmit.slot;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author xz man
 * @date 2018/7/19 下午4:06
 * 水槽导流器
 *
 */
public class FlumeBootStream {

    /**
     * 容器
     */
    private static final ThreadLocal<Map<String,String>>  CACHE_CONTAINER = new ThreadLocal<>();



    private static void initContainer(){

        //初始化容器对象
        Map<String,String>  parameterContainer = CACHE_CONTAINER.get();
        if(parameterContainer == null){
            CACHE_CONTAINER.set(new HashMap<>(8));
        }

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
        //初始化
        initContainer();

        Map<String,String>  parameterContainer = CACHE_CONTAINER.get();
        parameterContainer.put(key, value);
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
        //初始化
        initContainer();
        Map<String,String>  parameterContainer = CACHE_CONTAINER.get();
        parameterContainer.putAll(headers);
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

        Map<String,String>  parameterContainer = CACHE_CONTAINER.get();
        return parameterContainer.get(key);
    }

    /**
     *
     * @author xz man
     * @date 2018/7/19 下午4:19
     * 获取到参数
     *
     */
    public static Map<String,String> getParameter(){

        Map<String,String>  parameterContainer = CACHE_CONTAINER.get();
        return parameterContainer;
    }

    /**
     *
     * @author xz man
     * @date 2018/7/19 下午4:29
     * 清除资源
     *
     */
    public static void clear(){

        CACHE_CONTAINER.remove();
    }
}
