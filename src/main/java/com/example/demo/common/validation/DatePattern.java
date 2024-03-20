package com.example.demo.common.validation;

import com.example.demo.common.Constant;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DatePattern {
    String message() default Constant.DATE_VALID;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
