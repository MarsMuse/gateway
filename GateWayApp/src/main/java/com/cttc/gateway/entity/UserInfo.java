package com.cttc.gateway.entity;

import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Data;

/**
 *
 * @author zou yao
 * @date 2018/6/28 下午2:20
 * 测试不可变类
 *
 */
@Data
@Immutable
public class UserInfo {

    private String name;

    private int age;

    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }


}
