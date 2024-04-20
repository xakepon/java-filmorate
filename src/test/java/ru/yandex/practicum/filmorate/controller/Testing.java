package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import ru.yandex.practicum.filmorate.storage.FilmDBStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;





@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class Testing {

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testFindFilmById() {
        Film film = new Film("2007",
                "Give me back my 2007",
                LocalDate.of(2007, 7, 7),
                Duration.ofSeconds(100));
        HashSet<Long> like = new HashSet<>();
        like.clear();
        FilmDBStorage filmDBStorage = new FilmDBStorage(jdbcTemplate);
        filmDBStorage.addFilm(film);
        Film savedFilm = filmDBStorage.getFilmById(1);
        savedFilm.setLikes(like);
        assertThat(savedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(film);
    }
}
