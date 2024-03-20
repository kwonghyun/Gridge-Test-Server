package com.example.demo.common.validation;


import com.example.demo.common.Constant;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Slf4j
public class DateValidator implements ConstraintValidator<DatePattern, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            LocalDate.parse(value, Constant.DATE_FORMAT);
        } catch (DateTimeParseException e) {
            log.error("DateValidator : {}", e.getMessage());
            return false;
        }
        return true;
    }
}