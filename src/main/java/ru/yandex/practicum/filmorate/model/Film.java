package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.yandex.practicum.filmorate.controller.MyDurationSerializer;
import ru.yandex.practicum.filmorate.validate.ValidDate;
import ru.yandex.practicum.filmorate.validate.ValidDuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */

@Data
public class Film {
    @NonNull
    private int id; // целочисленный идентификатор фильма

    @NonNull
    @NotNull(message = "название фильма не null")
    @NotBlank(message = "название фильма не пустое")
    private String name; // название фильма

    @NonNull
    @Size(max = 200, message = "максимальный размер описания фильма — 200 символов")
    @NotNull(message = "описание фильма не null")
    private String description;  // описание фильма

    @NonNull
    @NotNull(message = "релиз фильма не null")
    @ValidDate(date = "1895-12-28")
    private LocalDate releaseDate; // дата релиза фильма

    @NonNull
    @NotNull(message = "продолжительность фильма не null")
    @ValidDuration
    @JsonSerialize(using = MyDurationSerializer.class)
    private Duration duration; // продолжительность фильма

    private Set<Long> likes = new HashSet<>();

    private static int countOfFilm = 0;

    @JsonProperty("mpa")
    private Mpa mpa;
    @JsonProperty("genres")
    private Set<Genre> genres = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, Duration duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Set<Long> getLikes() {
        Set<Long> newLikes = likes;
        return newLikes;
    }

    @Data
    public static class Mpa {
        private int id;
        private String name;
    }

    @Data
    public static class Genre {
        private int id;
        private String name;
    }
}