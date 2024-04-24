package ru.yandex.practicum.filmorate.controller;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class FilmTest {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void addInvalidNameFilmTest() {
        Film film = new Film(
                null,
                "Give me back my 2007",
                LocalDate.of(2008, 5, 6),
                Duration.ofSeconds(234));

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(2, violations.size());
    }

    @Test
    public void addInvalidYearFilmTest() {
        Film film = new Film(
                "Film1",
                "DescriptionOfFilm1",
                LocalDate.of(456, 5, 6),
                Duration.ofSeconds(234));

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }

    @Test
    public void addInvalidDurationFilmTest() {
        Film film = new Film(
                "Film1",
                "DescriptionOfFilm1",
                LocalDate.of(2008, 5, 6),
                Duration.ofSeconds(-1));

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }
}