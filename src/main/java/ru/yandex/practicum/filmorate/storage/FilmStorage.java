package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validate.ValidException;

import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film) throws ValidException;

    Film updateFilm(Film film) throws ValidException;

    List<Film> getAllFilms();
}