package ru.yandex.practicum.filmorate.controller;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void addInvalidEmailUserTest() {
        User user = new User(
                "I n v a l i d",
                "login1",
                "name1",
                LocalDate.of(1989, 2, 10));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void addInvalidLoginUserTest() {
        User user = new User(
                "gmail@mail.ru",
                "I n v a l i d",
                "name1",
                LocalDate.of(1989, 2, 10));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void addInvalidNameUserTest() {
        User user = new User(
                "gmail@mail.ru",
                "login1",
                "",
                LocalDate.of(1989, 2, 10));
        assertEquals("login1", user.getName());
    }

    @Test
    public void addInvalidDateUserTest() {
        User user = new User(
                "gmail@mail.ru",
                "login1",
                "name1",
                LocalDate.of(2345, 2, 10));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }
}
