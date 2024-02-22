package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.controller.MyDurationSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.Duration;
import java.time.LocalDate;

/**
 * Film.
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Film {

    MyDurationSerializer myDurationSerializer = new MyDurationSerializer();
    
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

    private Duration duration; // продолжительность фильма

}
