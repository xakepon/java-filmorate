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
    private int filmID = 0;

    public List<Film> getAllFilms() {
        return List.copyOf(films.values());
    }

    public void addFilm(Film film) throws ValidException {
        checkFilm(film);
        film.setId(++filmID);
        films.put(film.getId(), film);
    }

    public void updateFilm(Film film) throws ValidException {
        if (!films.containsKey(film.getId())) {
            log.warn("Фильм с айди {} не найден", film.getId());
            throw new ValidException("Фильм с айди не найден");
        }
        checkFilm(film);
        films.put(film.getId(), film);
    }

    private void checkFilm(Film film) throws ValidException {
        if (films.containsValue(film)) {
            log.info("Ошибка проверки фильма " + film.getName() + ". Фильм уже добавлен в пееречень");
            throw new ValidException("Фильм уже добавлен в перечень");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Дата фильма " + film.getName() + " выходит за текущую дату в будущее");
            throw new ValidException("Дата выхода фильма не корректная, из будущего");
        }
        if (film.getDuration().getSeconds() <= 0) {
            log.info("Продолжительность фильма " + film.getName() + " не положительное число");
            throw new ValidException("Продолжительность фильма не положительное число");
        }
    }
}
