package com.cttc.auth.constant;

/**  
 *    
 * @author xz man 
 * @date 2018/7/23 下午3:42
 * @since v1.0
 * 登录常量
 * 
 */ 
public enum SignInConstants {


    WEB_LOGIN_NAME("loginName","Web端登录名"),

    WEB_PASSWORD("password","web端登录密码"),

    WEB_SOURCE_USER("1","web端用户"),

    APP_LOGIN_NAME("loginName","APP端登录名"),

    APP_PASSWORD("password","APP端登录密码"),

    APP_SOURCE_USER("2","APP端用户");

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 描述
     */
    private String describe;

    private SignInConstants(String formName, String describe) {
        this.formName = formName;
        this.describe = describe;
    }

    public String getFormName() {
        return formName;
    }
}
