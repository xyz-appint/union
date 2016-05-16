package com.zuoyouxuan;

import java.lang.annotation.*;

/**
 * Created by justin on 15/9/18.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Col {
    String name();
}
