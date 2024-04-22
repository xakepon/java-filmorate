package ru.yandex.practicum.filmorate.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidatingDate.class)
public @interface ValidDate {

    String message() default "Дата фидьма должна быть не раньше 28 декабря 1895 года";

    String date();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}