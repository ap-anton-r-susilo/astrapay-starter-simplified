package com.astrapay.starter.configuration.mapper;

import java.lang.annotation.*;

/**
 * ParamName
 *
 * @author  Fawwaazrahman Arandhana W
 * @author  Astrapay
 * @version 1.12
 * @since   2021-09-01
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamName {

    /**
     * The name of the request parameter to bind to.
     */
    String value();
}
