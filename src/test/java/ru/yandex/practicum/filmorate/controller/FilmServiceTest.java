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

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmServiceTest {

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testFindFilmById() {
        Film film = new Film("2019",
                "description1",
                LocalDate.of(2009, 1, 2),
                Duration.ofSeconds(2900));
        HashSet<Long> like = new HashSet<>();
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
