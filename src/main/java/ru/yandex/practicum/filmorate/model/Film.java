package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Film.
 */

@Data
@AllArgsConstructor
public class Film {

    private int id; // целочисленный идентификатор фильма

    @NotNull(message = "название фильма не null")
    @NotBlank(message = "название фильма не пустое")
    private String name; // название фильма

    @Size(max = 200, message = "максимальный размер описания фильма — 200 символов")
    @NotNull(message = "описание фильма не null")
    private String description;  // описание фильма

    @NotNull(message = "релиз фильма не null")
    private LocalDate releaseDate; // дата релиза фильма

    @NotNull(message = "продолжительность фильма не null")
    @Positive(message = "продолжительность фильма должна быть положительной")
    private long duration; // продолжительность фильма

}
