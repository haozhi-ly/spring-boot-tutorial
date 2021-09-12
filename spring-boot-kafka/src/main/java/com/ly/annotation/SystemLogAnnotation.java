package com.ly.annotation;

import java.lang.annotation.*;

/**
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLogAnnotation {
    // 菜单
    String menu() default "";
    //操作
    String operation() default "";

}
