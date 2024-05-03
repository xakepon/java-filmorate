package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDBStorage;


import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

   private final UserDBStorage userDBStorage;

    public User addUser(User user) {
        return userDBStorage.addUser(user);
    }

    public User updateUser(User user) {
        return userDBStorage.updateUser(user);
    }

    public ArrayList<User> getAllUsers() {
        return userDBStorage.getAllUsers();
    }

    public User getUserById(int idUser) {
        return userDBStorage.getUserById(idUser);
    }

    public void addFriend(int idUser, int idFriend) {
        log.info("Пользователь {} пытается добавил {} в список друзей.", idUser, idFriend);
        User user = userDBStorage.getUserById(idUser);
        if (user.getFriends() == null) {
            user.setFriends(new HashMap<>());
        }
        user.setFriends(userDBStorage.addFriend(idUser, idFriend));
        userDBStorage.updateUser(user);
    }


    public void delFriend(int idUser, int idFriend) {
        log.info("Пользователь {} пытается удалить {} из списка друзей.", idUser, idFriend);
        User user = userDBStorage.getUserById(idUser);

        if (user.getFriends().isEmpty()) {
            return;
        }

        if (!user.getFriends().containsKey(idFriend)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        user.getFriends().remove(idFriend);
        log.info("Пользователь {}  удалил {} из списка друзей в объекте.", idUser, idFriend);
        if (user.getFriends() != null
                && user.getFriends().containsKey(idUser)) {
            user.setFriends(userDBStorage.deleteFriend(idUser, idFriend));
            log.info("Пользователь {}  удалил {} из списка друзей в БД.", idUser, idFriend);
        }
        userDBStorage.updateUser(user);
    }

    public ArrayList<User> getFriends(int idUser) {
        User user = userDBStorage.getUserById(idUser);
        ArrayList<User> friends = new ArrayList<>();
        for (int part : user.getFriends().keySet()) {
            User listUser = userDBStorage.getUserById(part);
            friends.add(listUser);
        }
        return friends;
    }

    public ArrayList<User> getLargeFriends(int idUser1, int idUser2) {
        Map<Integer, String> user1 = getUserById(idUser1).getFriends();
        Map<Integer, String> user2 = getUserById(idUser2).getFriends();

        ArrayList<User> largeFriends = new ArrayList<>();

        for (Integer key : user1.keySet()) {
            if (user2.containsKey(key)) {
                largeFriends.add(getUserById(key));
            }
        }
        return largeFriends;
    }
}
