package xyz.appint.union.dao.dao.annotation;

import java.lang.annotation.*;

/**
 * Created by justin on 15/9/18.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Col {
    String name() default "";

    String value() default "";

    Include include() default Include.ALWAYS;

//    String defaultVal() default "";

    public static enum Include {
        ALWAYS,
        NON_NULL,
        NON_EMPTY,
        NON_DEFAULT;

        private Include() {
        }
    }
}
