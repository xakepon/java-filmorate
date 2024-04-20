package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmDBStorage;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {

    private final FilmDBStorage filmDBStorage;

    public Film addFilm(Film film) {
       return filmDBStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
     return filmDBStorage.updateFilm(film);
    }

    public ArrayList<Film> getAllFilms() {
        return filmDBStorage.getAllFilms();
    }

    public Film getFilmById(int filmId) {
        return filmDBStorage.getFilmById(filmId);
    }

    public void settingLike(int idUser, int idFilm) {
        log.info("Gользователь {} пытается поставить лайк фильму {}", idUser, idFilm);
        Film film = filmDBStorage.getFilmById(idFilm);
        if (film.getLikes()
                .stream()
                .anyMatch(id -> id == idUser)) {
            log.info("Пользователь {} не смог поставил лайк фильму {}, так как лайк уже стоит", idUser, idFilm);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Лайк уже стоит");
        }
        Set<Long> likes = film.getLikes();
        likes.add((long) idUser);
        film.setLike(idUser);
            log.info("Пользователь {} поставил лайк фильму {}", idUser, idFilm);
    }

    public void delLike(int idUser, int idFilm) {
        log.info("Попытка пользователя {} удалить лайк у фильма {}", idUser, idFilm);
        Film film = filmDBStorage.getFilmById(idFilm);
        Set<Long> likes = film.getLikes();
        likes.remove((long) idUser);
        film.setLikes(likes);
        if (film.getLikes()
                .stream()
                .anyMatch(id -> id == idUser) && idUser > 0 || film.getLikes().isEmpty()) {
            filmDBStorage.deleteLike(idUser, idFilm);
            log.info("Пользователь {} убрал лайк у фильма {}", idUser, idFilm);
            filmDBStorage.updateFilm(film);
        } else {
            log.info("Пользователь {} не смог убрать лайк у фильма {}", idUser, idFilm);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "не смог убрать лайк у фильма");
        }
    }

    public List<Film> getLargeLikedFilms(int count) {
        log.info("Вывод самых {} популярных фильмов", count);
        ArrayList<Film> films = filmDBStorage.getAllFilms();
       /* Collections.sort(films,
                Comparator.comparingInt((Film film) -> film.getLikes().size()).reversed());
        return films.subList(0, Math.min(films.size(), count));*/
        films.sort((film1, film2) -> {
            if (film1.getLikes().contains(0)) {
                return film2.getLikes().contains(0) ? Integer.compare(film2.getLikes().size(), film1.getLikes().size()) : 1;
            } else if (film2.getLikes().contains(0)) {
                return -1;
            } else {
                return Integer.compare(film2.getLikes().size(), film1.getLikes().size());
            }
        });
        return films.subList(0, Math.min(films.size(), count));
    }

    public List<Map<String, Object>> getMpa() {
        log.info("Вывод всех рейтингов и id");
        List<Map<String, Object>> arrayMPA = new ArrayList<>();
        Map<Integer, String> mapMPA = filmDBStorage.getMpa();
        for (Map.Entry<Integer, String> entry : mapMPA.entrySet()) {
            Map<String, Object> mpaInfo = new HashMap<>();
            mpaInfo.put("id", entry.getKey());
            mpaInfo.put("name", entry.getValue());
            arrayMPA.add(mpaInfo);
        }
        return arrayMPA;
    }

    public Map<String, Object> getMpaById(int idMPA) {
        log.info("Вывод рейтинга с id {}", idMPA);
        Map<Integer, String> mapMPA = filmDBStorage.getMpaById(idMPA);
        if (!mapMPA.isEmpty()) {
            Map<String, Object> mpaInfo = new HashMap<>();
            for (Map.Entry<Integer, String> entry : mapMPA.entrySet()) {
                mpaInfo.put("id", entry.getKey());
                mpaInfo.put("name", entry.getValue());
            }
            return mpaInfo;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Список рейтинга пустой.");
        }
    }

    public List<Map<String, Object>> getGenres() {
        log.info("Вывод жанров и id");
        List<Map<String, Object>> arrayGenre = new ArrayList<>();
        Map<Integer, String> mapGenre = filmDBStorage.getGenres();
        for (Map.Entry<Integer, String> entry : mapGenre.entrySet()) {
            Map<String, Object> genreInfo = new HashMap<>();
            genreInfo.put("id", entry.getKey());
            genreInfo.put("name", entry.getValue());
            arrayGenre.add(genreInfo);
        }
        return arrayGenre;
    }

    public Map<String, Object> getGenreById(int idGenre) {
        log.info("Вывод жанра с id {}", idGenre);
        Map<Integer, String> mapGenre = filmDBStorage.getGenreById(idGenre);
        if (!mapGenre.isEmpty()) {
            Map<String, Object> genreResult = new HashMap<>();
            for (Map.Entry<Integer, String> entry : mapGenre.entrySet()) {
                genreResult.put("id", entry.getKey());
                genreResult.put("name", entry.getValue());
            }
            return genreResult;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Список жанров пустой.");
        }
    }
}