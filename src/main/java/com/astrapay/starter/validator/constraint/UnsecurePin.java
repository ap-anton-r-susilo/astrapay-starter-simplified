package com.astrapay.starter.validator.constraint;

import com.astrapay.starter.validator.UnsecurePinValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {UnsecurePinValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnsecurePin {

    String code() default "UNSECURE_PIN";

    String message() default "Unsecure pin";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
