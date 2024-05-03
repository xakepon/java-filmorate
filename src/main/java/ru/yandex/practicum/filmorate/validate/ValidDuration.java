package ru.yandex.practicum.filmorate.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidatingDuration.class)
public @interface ValidDuration {
    String message() default "Продолжительность должна быть больше нуля";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}