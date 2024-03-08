package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
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
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{idFilm}/like/{idUser}")
    public void giveFilmLike(@PathVariable int idFilm,
                               @PathVariable int idUser) {
        filmService.settingLike(idUser, idFilm);
    }

    @DeleteMapping("/{idFilm}/like/{idUser}")
    public void delLike(@PathVariable int idFilm,
                           @PathVariable int idUser) {
        filmService.delLike(idUser, idFilm);
    }

    @GetMapping("/popular")
    public List<Film> getBestFilms(
            @RequestParam(defaultValue = "10") Integer count) {
        return filmService.getLargeLikedFilms(count);
    }

}
