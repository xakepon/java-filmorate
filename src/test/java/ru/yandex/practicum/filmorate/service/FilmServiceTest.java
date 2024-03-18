package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.Duration;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmServiceTest {
    @Test
    public void testFindFilmById() {
        Film film = new Film("2024",
                "Test",
                LocalDate.of(2016, 1, 2),
                Duration.ofSeconds(1000));
        InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        inMemoryFilmStorage.addFilm(film);
        Film savedFilm = inMemoryFilmStorage.getFilmById(film.getId());
        assertThat(savedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(film);
    }
}