package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.Before;
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
import static org.assertj.core.api.Assertions.assertThat;
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    
    FilmController filmController = new FilmController(new FilmService(new InMemoryFilmStorage()));

    @Test
    @DisplayName(value = "Проверка создания фильма с корректными данными")
    public void addFilmValidData() {

        Film film = new Film("Name1", "Description1",
                LocalDate.of(2022, 5, 29), Duration.ofSeconds(1000));
        filmController.addFilm(film);
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName(value = "Проверка создания фильма с не корректными данными")
    public void addFilmInValidData() {
        Film film = new Film(null, "Description2",
                LocalDate.of(2025, 3, 4),Duration.ofSeconds(3000));

        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}