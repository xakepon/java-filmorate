package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;


import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final InMemoryUserStorage inMemoryUserStorage;

    public User addUser(User user) {
        return inMemoryUserStorage.addUser(user);
    }

    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    public ArrayList<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    public User getUserById(int idUser) {
        return inMemoryUserStorage.getUserById(idUser);
    }

    public void addFriend(int idUser, int idFriend) {
        log.info("Пользователь {} добавил {} в список друзей.", idUser, idFriend);
        User user = inMemoryUserStorage.getUserById(idUser);
        User friend = inMemoryUserStorage.getUserById(idFriend);
        user.addFriend(idFriend);
        friend.addFriend(idUser);
    }


    public void delFriend(int idUser, int idFriend) {
        log.info("Пользователь {} удалил {} из списка друзей.", idUser, idFriend);
        User user = inMemoryUserStorage.getUserById(idUser);
        User friend = inMemoryUserStorage.getUserById(idFriend);
        user.delFriend(idFriend);
        friend.delFriend(idUser);
    }

    public ArrayList<User> getFriends(int idUser) {
        User user = inMemoryUserStorage.getUserById(idUser);
        ArrayList<User> friends = new ArrayList<>();
        for (long part : user.getFriends()) {
            User listUser = getUserById((int) part);
            friends.add(listUser);
        }
        return friends;
    }

    public ArrayList<User> getLargeFriends(int idUser1, int idUser2) {
        User user1 = inMemoryUserStorage.getUserById(idUser1);
        User user2 = inMemoryUserStorage.getUserById(idUser2);
        Set<Long> set = new HashSet<>(user1.getFriends());
        set.retainAll(user2.getFriends());
        ArrayList<User> largeFriends = new ArrayList<>();
        for (long part : set) {
            User user = inMemoryUserStorage.getUserById((int) part);
            largeFriends.add(user);
        }
        return largeFriends;
    }
}
