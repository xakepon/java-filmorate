package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validate.ValidException;

import java.util.List;

public interface UserStorage {

    User addUser(User object) throws ValidException;

    User updateUser(User user) throws ValidException;

    List<User> getAllUsers();

    public User getUserById(int idUser);
}
