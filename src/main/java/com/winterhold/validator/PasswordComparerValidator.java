package com.winterhold.validator;


import com.winterhold.dto.account.RegisterDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordComparerValidator implements
        ConstraintValidator<PasswordComparer, RegisterDTO> {

    @Override
    public void initialize(PasswordComparer constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterDTO dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}
