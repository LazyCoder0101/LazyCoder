package com.lazycoder.service;

/**
 * 模块使用设置
 */
public interface ModuleUseSetting {

    /**
     * 必选
     */
//    public static final int MUST_USE = 0;
    //取消必选属性，避免两个不一起使用的模块都被选择该属性


    /**
     * 对用户屏蔽
     */
    public static final int USER_SHIELDING = 1;

}
