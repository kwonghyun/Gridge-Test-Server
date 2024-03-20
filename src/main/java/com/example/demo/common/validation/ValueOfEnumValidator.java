package com.example.demo.common.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ValueOfEnumValidator implements ConstraintValidator<EnumValueCheck, String> {

    private static final Logger log = LoggerFactory.getLogger(EnumValueCheck.class);
    private EnumValueCheck enumValueCheck;
    @Override
    public void initialize(EnumValueCheck constraintAnnotation) {
        this.enumValueCheck = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            Method fromMethod = this.enumValueCheck.enumClass().getMethod("valueOf", Class.class, String.class);
            fromMethod.invoke(null, value.toUpperCase());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("Error validating enum value: {}", e.getMessage());
            return false;
        }
        return true;
    }
}