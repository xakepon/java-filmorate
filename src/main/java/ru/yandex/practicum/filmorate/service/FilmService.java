package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validate.ValidException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FilmService {
    private final Map<Integer, Film> films = new HashMap<>();
    private static int FILM_ID = 0;

    public List<Film> getAllFilms() {
        return List.copyOf(films.values());
    }

    public void addFilm(Film film) throws ValidException {
        checkFilm(film);
        dateValidFilm(film);
        film.setId(++FILM_ID);
        films.put(film.getId(), film);
    }

    public void updateFilm(Film film) throws ValidException {
        checkFilm(film);
        dateValidFilm(film);
        films.put(film.getId(), film);
    }

    private void dateValidFilm(Film film) throws ValidException {
        if (film.getReleaseDate().isBefore(LocalDate.now())) {
            log.info("Дата фильма " + film.getName() + " выходит за текущую дату в будущее");
            throw new ValidException("Дата выхода фильма не корректная, из будущего");
        }
    }

    private void checkFilm(Film film) throws ValidException {
        if (films.containsValue(film)) {
            log.info("Ошибка проверки фильма " + film.getName() + ". Фильм уже добавлен в пееречень");
            throw new ValidException("Фильм уже добавлен в перечень");
        }
    }
}
