package com.lazycoder.utils.compare;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 摘自： https://blog.csdn.net/sunnyzyq/article/details/124603360
 * 字段标记注解
 *
 * @author zyqok
 * @since 2022/05/05
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Compare {

    /**
     * 字段名称
     */
    String value();

}