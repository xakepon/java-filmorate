package ru.yandex.practicum.filmorate.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidatingDate implements ConstraintValidator<ValidDate, LocalDate> {

    private String dateParameter;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        this.dateParameter = constraintAnnotation.date();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        LocalDate specifiedDate = LocalDate.parse(dateParameter);
        return value.isAfter(specifiedDate);
    }
}
