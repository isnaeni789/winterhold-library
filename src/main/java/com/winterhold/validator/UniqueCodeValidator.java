package com.winterhold.validator;

import com.winterhold.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCodeValidator
        implements ConstraintValidator<UniqueCode, String> {

    @Autowired
    private BookService service;

    @Override
    public void initialize(UniqueCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return service.isValidBookCode(value);
    }
}
