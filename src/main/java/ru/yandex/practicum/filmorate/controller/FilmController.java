package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    // добавление фильма
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    // обновление фильма;
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    // получение всех фильмов
    @GetMapping
    public ArrayList<Film> getAllFilms() {
        return (ArrayList<Film>) filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int idFilm) {
        return filmService.getFilmById(idFilm);
    }

    @PutMapping("/{idFilm}/like/{idUser}")
    public void giveFilmLike(@PathVariable int idFilm,
                               @PathVariable int idUser) {
        filmService.gettingLike(idUser, idFilm);
    }

    @DeleteMapping("/{idFilm}/like/{idUser}")
    public void delLike(@PathVariable int idFilm,
                           @PathVariable int idUser) {
        filmService.delLike(idUser, idFilm);
    }

    @GetMapping("/popular")
    public List<Film> getBestFilms(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        return filmService.getLargeLikedFilms(count);
    }

}
