package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validate.ValidException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int filmID = 0;

    @Override
    public Film addFilm(Film film) throws ValidException {
        checkFilm(film);
        film.setId(++filmID);
        films.put(film.getId(), film);
        log.debug("Фильм был добавлен");
        return film;
    }
    @Override
    public Film updateFilm(Film film) throws ValidException {
        if (!films.containsKey(film.getId())) {
            log.warn("Фильм с айди {} не найден", film.getId());
            throw new ValidException("Фильм с айди не найден");
        }
        checkFilm(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        }
        log.debug("Фильм обновлен");
        return film;
    }
    @Override
    public List<Film> getAllFilms() {
        log.debug("Список всех фильмов получен");
        return List.copyOf(films.values());
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        return null;
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
