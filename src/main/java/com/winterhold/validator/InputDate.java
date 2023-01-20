package com.winterhold.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = InputDateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(InputDates.class)
public @interface InputDate {

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message();
    String previousDateField();
    String subsequentDateField();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        InputDate[] value();
    }
}
