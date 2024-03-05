package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {

    private final InMemoryFilmStorage inMemoryFilmStorage;

    public Film addFilm(Film film) {
       return inMemoryFilmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
     return inMemoryFilmStorage.updateFilm(film);
    }

    public ArrayList<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    public Film getFilmById(int filmId) {
        return inMemoryFilmStorage.getFilmById(filmId);
    }

    public void gettingLike(int idUser, int idFilm) {
        log.info("Gользователь {} пытается поставить лайк фильму {}", idUser, idFilm);
        Film film = getFilmById(idFilm);
        if (film.getLikes()
                .stream()
                .anyMatch(id -> id == idUser)) {
            log.info("Пользователь {} не смог поставил лайк фильму {}", idUser, idFilm);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм не был найден");
        } else {
            film.setLike(idUser);
            log.info("Пользователь {} поставил лайк фильму {}", idUser, idFilm);
        }
    }

    public void delLike(int idUser, int idFilm) {
        log.info("Попытка пользователя {} удалить лайк у фильма {}", idUser, idFilm);
        Film film = getFilmById(idFilm);
        if (film.getLikes()
                .stream()
                .anyMatch(id -> id == idUser)) {
            film.delLike(idUser);
            log.info("Пользователь {} убрал лайк у фильма {}", idUser, idFilm);
        } else {
            log.info("Пользователь {} не смог убрать лайк у фильма {}", idUser, idFilm);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм не найден");
        }
    }

    public List<Film> getLargeLikedFilms(int count) {
        log.info("Вывод самых {} популярных фильмов", count);
        ArrayList<Film> films = getAllFilms();
        Collections.sort(films,
                Comparator.comparingInt((Film film) -> film.getLikes().size()).reversed());
        return films.subList(0, Math.min(films.size(), count));
    }
}
