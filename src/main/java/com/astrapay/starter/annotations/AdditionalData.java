package com.astrapay.starter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AdditionalData {
    String value() default "";
    String label() default "";
    boolean isCopyable() default false;
}
