package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validate.ValidException;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService = new UserService();

    //создание пользователя
    @PostMapping
    public ResponseEntity<User> addUser(@Validated @RequestBody User user) {
        try {
            userService.addUser(user);
            log.info("Пользователь " + user.getName() + " добавлен");
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (ValidException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
    }

    //обновление пользователя
    @PutMapping
    public ResponseEntity<User> updateUser(@Validated @RequestBody User user) {
        try {
            userService.updateUser(user);
            log.info("Пользователь " + user.getName() + " обновлен");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ValidException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
    }

    //получение списка всех пользователей
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Получен список всех пользователей");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

}
