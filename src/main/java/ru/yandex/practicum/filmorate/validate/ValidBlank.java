package ru.yandex.practicum.filmorate.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidatingBlank.class)
public @interface ValidBlank {
    String message() default "Не должно быть пробелов";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}