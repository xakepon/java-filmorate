package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Genre {

    private int id;

    @NotNull
    @NotBlank(message = "жанр фильма не должен быть пустым")
    private String name;
}
