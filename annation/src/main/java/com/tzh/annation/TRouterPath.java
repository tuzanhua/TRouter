package com.tzh.annation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create by tuzanhua on 2020/4/18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface TRouterPath {
    String value();
}
