package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
public class User {
    @NonNull
    private int id; // целочисленный идентификатор пользователя

    @NonNull
    @NotNull(message = "электронная почта не null")
    @NotBlank(message = "электронная почта не пустая")
    private String email; // электронная почта пользователя

    @NonNull
    @NotNull(message = "имя пользовтеля не null")
    @NotBlank(message = "имя пользовтеля не пустое")
    private String login; // логин пользователя

    @NonNull
    private String name; // имя пользователя для отображения

    @NonNull
    @NotNull(message = "дата рождения пользовтеля не может быть null")
    @Past(message = "дата рождения пользователя дальше текущей даты")
    private LocalDate birthday; // дата рождения пользователя

    private Set<Long> friends = new HashSet<>();

    public void addFriend(long id) {
        friends.add(id);
    }

    public void delFriend(long id) {
        friends.remove(id);
    }
}
