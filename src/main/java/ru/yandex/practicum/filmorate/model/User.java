package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {

    private int id; // целочисленный идентификатор пользователя

    @NotNull(message = "электронная почта не null")
    @NotBlank(message = "электронная почта не пустая")
    private String email; // электронная почта пользователя

    @NotNull(message = "имя пользовтеля не null")
    @NotBlank(message = "имя пользовтеля не пустое")
    private String login; // логин пользователя

    private String name; // имя пользователя для отображения

    @NotNull(message = "дата рождения пользовтеля не может быть null")
    @Past(message = "дата рождения пользователя дальше текущей даты")
    private LocalDate birthday; // дата рождения пользователя

}
