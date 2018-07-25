package com.cttc.auth.entity;

import lombok.Data;

/**
 *
 * @author xz man
 * @date 2018/7/23 上午11:51
 * 用户信息--存储前端需要的基本信息
 *
 */
@Data
public class UserInfo {

    /**
     * 主键
     */
    private String id;

    /**
     * 登录名
     */

    private String loginName;

    /**
     * 用户来源（1:web端 ,2: 移动端）
     */
    private String userSource;
}
