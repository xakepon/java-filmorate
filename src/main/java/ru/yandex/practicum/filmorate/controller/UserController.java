package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //создание пользователя
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    //обновление пользователя
    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    //получение списка всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{idUser}")
    public User getUser(@PathVariable int idUser) {
        return userService.getUserById(idUser);
    }

    @PutMapping("/{idUser}/friends/{idFriend}")
    public void addFriend(@PathVariable int idUser,
                          @PathVariable int idFriend) {
        userService.addFriend(idUser, idFriend);
    }

    @DeleteMapping("/{idUser}/friends/{idFriend}")
    public void deleteFriend(@PathVariable int idUser,
                             @PathVariable int idFriend) {
        userService.delFriend(idUser, idFriend);
    }

    @GetMapping("/{idUser}/friends")
    public ArrayList<User> getFriends(@PathVariable int idUser) {
        return userService.getFriends(idUser);
    }

    @GetMapping("/{idUser}/friends/common/{idOther}")
    public ArrayList<User> getLargeFriends(@PathVariable int idUser,
                                            @PathVariable int idOther) {
        return userService.getLargeFriends(idUser, idOther);
    }
}
