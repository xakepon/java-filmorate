package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validate.ValidException;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class FilmService {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmID = 0;


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

    public List<Film> getAllFilms() {
        return List.copyOf(films.values());
    }

    public Film getFilmById(int id) {
        return films.get(id);
    }

    public void giveLike(int userId, int filmId) {
        log.info("Попытка пользователя {} поставить лайк фильму {}.", userId, filmId);
        Film film = getFilmById(filmId);
        if (film.getLikes()
                .stream()
                .anyMatch(id -> id == userId)) {
            log.info("Пользователь {} не смог поставил лайк фильму {}.", userId, filmId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм не найден");
        } else {
            film.setLike(userId);
            log.info("Пользователь {} поставил лайк фильму {}.", userId, filmId);
        }
    }

    public void delLike(int userId, int filmId) {
        log.info("Попытка пользователя {} удалить лайк у фильма {}.", userId, filmId);
        Film film = getFilmById(filmId);
        if (film.getLikes()
                .stream()
                .anyMatch(id -> id == userId)) {
            film.delLike(userId);
            log.info("Пользователь {} удалил лайк у фильма {}.", userId, filmId);
        } else {
            log.info("Пользователь {} не смог удалить лайк у фильма {}.", userId, filmId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм не найден");
        }
    }

    public List<Film> getMostLikedFilms(Integer count) {
        log.info("Вывод топ {} популярных фильмов .", count);
        ArrayList<Film> films = (ArrayList<Film>) getAllFilms();
        Collections.sort(films,
                Comparator.comparingInt((Film film) -> film.getLikes().size()).reversed());
        return films.subList(0, Math.min(films.size(), count));
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
