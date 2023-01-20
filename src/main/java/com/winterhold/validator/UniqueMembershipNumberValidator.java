package com.winterhold.validator;

import com.winterhold.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueMembershipNumberValidator
        implements ConstraintValidator<UniqueMembershipNumber, String> {

    @Autowired
    private CustomerService service;

    @Override
    public void initialize(UniqueMembershipNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return service.isValidMembershipNumber(value);
    }
}
