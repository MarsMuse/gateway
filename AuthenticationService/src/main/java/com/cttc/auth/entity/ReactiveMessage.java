package com.cttc.auth.entity;

import lombok.Data;

/**
 *
 * @author xz man
 * @date 2018/7/20 下午5:08
 *
 */
@Data
public class ReactiveMessage {

    /**
     * 返回编码（200：成功，300：失败）
     */
    private int code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public ReactiveMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ReactiveMessage() {
    }
    
    /**  
     *    
     * @author xz man 
     * @date 2018/7/23 下午4:08
     * @since v1.0
     * 方法描述: 操作失败
     *
     */ 
    public static ReactiveMessage operateError(){
        return new ReactiveMessage(300,"操作失败");
    }

    /**  
     *    
     * @author xz man 
     * @date 2018/7/23 下午4:09  
     * @since v1.0
     * 方法描述: 操作成功
     *
     */ 
    public static ReactiveMessage operateSuccess(){
        return new ReactiveMessage(200,"操作成功");
    }
}
