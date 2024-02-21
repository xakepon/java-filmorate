package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validate.ValidException;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class UserService {
    private final Map<Integer, User> users = new HashMap<>();
    private static int USER_ID = 0;

    public List<User> getAllUsers() {
        return List.copyOf(users.values());
    }

    public void addUser(User user) throws ValidException {
        checkUser(user);
        userValid(user);
        user.setId(++USER_ID);
        users.put(user.getId(), user);
    }

    public void updateUser(User user) throws ValidException {
        checkUser(user);
        userValid(user);
        users.put(user.getId(), user);
    }

    private void userValid(User user) throws ValidException {
        if (user.getLogin().contains(" ") || user.getLogin().isBlank()) {
            log.info("Логин не олжен быть пустым и содержать пробелов");
            throw new ValidException("Логин не олжен быть пустым и содержать пробелов");
        }
        if (!user.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:" +
                "\\.[a-zA-Z0-9_+&*-]+)*" + "@(?:[a-zA-Z0-9-]+" +
                "\\.)+[a-zA-Z]{2,7}$")) {
            log.info("Ошибка проверки электронной почты");
            throw new ValidException("Ошибка проверки электронной почты");
        }
    }

    private void checkUser(User user) throws ValidException {
        for (User mails : users.values()) {
            if (Objects.equals(mails.getEmail(), user.getEmail())) {
                log.info("Пользователь с такой почтой уже существует");
                throw new ValidException("Пользователь с такой почтой уже существует");
            }
        }
    }
}
