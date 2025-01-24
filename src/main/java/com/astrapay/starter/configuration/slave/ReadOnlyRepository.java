package com.astrapay.starter.configuration.slave;

import java.lang.annotation.*;

/**
 * @author Raymond Sugiarto
 * @since 2022-06-16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ReadOnlyRepository {
}
