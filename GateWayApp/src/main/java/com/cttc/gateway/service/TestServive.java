package com.cttc.gateway.service;

import com.cttc.gateway.entity.UserInfo;

public class TestServive {

    public static void main(String[] args) {
        UserInfo info = new UserInfo("a",1);
        System.out.println(info.hashCode());
        info.setAge(10);
        System.out.println(info);
    }
}
