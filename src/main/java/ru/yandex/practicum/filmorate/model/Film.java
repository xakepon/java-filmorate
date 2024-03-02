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
@RequiredArgsConstructor
@EqualsAndHashCode
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
    private Duration duration; // продолжительность ф'ильма

    private Set<Long> likes = new HashSet<>();

    public void setLike(long id) {
        likes.add(id);
    }

    public void delLike(long id) {
        likes.remove(id);
    }

}