package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@Validated
public class User {

    @NonNull
    private int id; // целочисленный идентификатор пользователя

    @NonNull
    @NotNull(message = "электронная почта не null")
    @NotBlank(message = "электронная почта не пустая")
    @Email
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

    private static int countOfUser = 0;

    //private Set<Long> friends = new HashSet<>();
    private Map<Integer, String> friends = new HashMap<>();

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
    /* public Set<Long> getFriends() {
       return Set.copyOf(friends);
    } */

    public Map<Integer, String> getFriends() {
        return Map.copyOf(friends);
    }

   /* public void addFriend(long id) {
        friends.add(id);
    }

    public void delFriend(long id) {
        friends.remove(id);
    }

    public int getCountOfUser() {
        return ++countOfUser;
    }*/
}
