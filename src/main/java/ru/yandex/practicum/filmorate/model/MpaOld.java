package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MpaOld {

    private int id;

    @NotBlank(message = "рейтинг фильма не должен быть пустым")
    @NotNull
    private String name;
}
