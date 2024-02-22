package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validate.ValidException;

import java.util.List;

@RestController
@RequestMapping(value = "/films")
@Slf4j
public class FilmController {

    private final FilmService filmService = new FilmService();
    MyDurationSerializer myDurationSerializer = new MyDurationSerializer();

   // добавление фильма
    @PostMapping
    public ResponseEntity<Film> addFilm(@Validated @RequestBody Film film) {
        try {
            filmService.addFilm(film);
            log.info("Добавлен фильм " + film.getName());
            return new ResponseEntity<>(film, HttpStatus.CREATED);
        } catch (ValidException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(film, HttpStatus.BAD_REQUEST);
        }
    }

    // обновление фильма;
    @PutMapping
    public ResponseEntity<Film> updateFilm(@Validated @RequestBody Film film) {
        try {
            filmService.updateFilm(film);
            log.info("Фильм " + film.getName() + " обновлен");
            return new ResponseEntity<>(film, HttpStatus.OK);
        } catch (ValidException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(film, HttpStatus.BAD_REQUEST);
        }
    }

    // получение всех фильмов
    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        log.info("Список всех фильмов получен");
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

}
