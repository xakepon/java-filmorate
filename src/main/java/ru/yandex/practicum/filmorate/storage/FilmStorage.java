package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validate.ValidException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FilmStorage {

    Film addFilm(Film film) throws ValidException;

    Film updateFilm(Film film) throws ValidException;

    Film getFilmById(int filmId);

    List<Film> getAllFilms();

    void deleteFilm(int filmId);

    Map<Integer, String> getMpa();

    Map<Integer, String> getMpaById(int genreId);

    Map<Integer, String> getGenres();

    Map<Integer, String> getGenreById(int genreId);

    Set<Integer> giveLike(int userId, int filmId);

    Set<Integer> deleteLike(int userId, int filmId);
}