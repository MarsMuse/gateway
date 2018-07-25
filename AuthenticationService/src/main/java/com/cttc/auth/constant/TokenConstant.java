package com.cttc.auth.constant;

/**
 *
 * @author xz man
 * @date 2018/7/16 下午3:32
 * token  支持算法
 *
 */
public enum TokenConstant {
    /**
     * HMAC using SHA-256 算法
     */
    HS256("HMAC using SHA-256 算法"),

    /**
     * HMAC using SHA-384 算法
     */
    HS384("HMAC using SHA-384 算法"),
    /**
     * HMAC using SHA-512 算法
     */
    HS512("HMAC using SHA-512 算法"),

    /**
     *
     */
    JWT("JSON WEB TOKEN");

    /**
     * 描述信息
     */
    private String describe;
    TokenConstant(String describe){ this.describe = describe;}

    /**
     * 获取到描述信息
     *
     */
    public String getDescribe() {
        return describe;
    }
}
