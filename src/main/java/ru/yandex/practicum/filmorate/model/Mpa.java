package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Mpa {

    private int id;

    @NotBlank(message = "рейтинг фильма не должен быть пустым")
    @NotNull
    private String name;
}
