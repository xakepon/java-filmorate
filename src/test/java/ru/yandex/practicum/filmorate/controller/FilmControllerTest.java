package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmControllerTest {
    FilmController filmController;
    Film film;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage()));
        film = new Film("Name1", "Description1",
                LocalDate.of(2022, 5, 29), Duration.ofSeconds(1000));
    }

    @Test
    @DisplayName(value = "Проверка создания фильма с корректными данными")
    public void addFilmValidData() {
        filmController.addFilm(film);
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // надо разобраться 201 CREAted
    }




    @Test
    @DisplayName(value = "Проверка создания фильма с не корректными данными")
    public void addFilmInValidData() {
        Film film = new Film(null, "Description2",
                LocalDate.of(2025, 3, 4),Duration.ofSeconds(3000));

        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()); //BAD_REQUEST
    }
}