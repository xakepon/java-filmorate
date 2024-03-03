package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validate.ValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int userId = 0;

    @Override
    public User addUser(User user) throws ValidException {
        checkUser(user);
        user.setId(++userId);
        users.put(user.getId(), user);
        log.debug("Пользовтель успешно добавлен");
        return user;
    }

    @Override
    public User updateUser(User user) throws ValidException {
        checkUser(user);
        users.put(user.getId(), user);
        log.debug("Пользовтель успешно обновлен");
        return user;
    }
    
    @Override
    public List<User> getAllUsers() {
        log.debug("Список всех пользователей получен");
        return List.copyOf(users.values());
    }

    @Override
    public List<User> getFriends() {
        return null;
    }

    private void checkUser(User user) throws ValidException {
        for (User mails : users.values()) {
            if (Objects.equals(mails.getEmail(), user.getEmail())) {
                log.info("Пользователь с такой почтой уже существует");
                throw new ValidException("Пользователь с такой почтой уже существует");
            }
        }
        if (user.getLogin().contains(" ")) {
            log.info("Логин не должен содержать пробелов");
            throw new ValidException("Логин не олжен быть пустым и содержать пробелов");
        }
        if (!user.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:" +
                "\\.[a-zA-Z0-9_+&*-]+)*" + "@(?:[a-zA-Z0-9-]+" +
                "\\.)+[a-zA-Z]{2,7}$")) {
            log.info("Ошибка проверки электронной почты");
            throw new ValidException("Ошибка проверки электронной почты");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            log.info("Поле имени не может быть пустым");
            user.setName(user.getLogin());
        }
    }

}
