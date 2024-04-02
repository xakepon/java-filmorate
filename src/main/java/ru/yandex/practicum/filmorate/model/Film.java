package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.yandex.practicum.filmorate.controller.MyDurationSerializer;

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
    private LocalDate releaseDate; // дата релиза фильма

    @NonNull
    @NotNull(message = "продолжительность фильма не null")
    @JsonSerialize(using = MyDurationSerializer.class)
    private Duration duration; // продолжительность фильма

    private Set<Long> likes = new HashSet<>();

    private static int countOfFilm = 0;

    public Film(String name, String description, LocalDate releaseDate, Duration duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Set<Long> getLikes() {
        //Set<Long> newLikes = likes;
        return Set.copyOf(likes);
    }

    public void setLike(long idUser) {
        likes.add(idUser);
    }

    public void delLike(long idUser) {
        likes.remove(idUser);
    }

    public int getcountFilm() {
        return ++countOfFilm;
    }
}