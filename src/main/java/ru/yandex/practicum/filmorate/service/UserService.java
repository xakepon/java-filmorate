package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
        //User friend = inMemoryUserStorage.getUserById(idFriend);
        //user.addFriend(idFriend);
        //friend.addFriend(idUser);
    }


    public void delFriend(int idUser, int idFriend) {
        log.info("Пользователь {} удалил {} из списка друзей.", idUser, idFriend);
        User user = userDBStorage.getUserById(idUser);
        user.getFriends().remove(idFriend);
        if (user.getFriends() != null
                && user.getFriends().containsKey(idUser)) {
            user.setFriends(userDBStorage.deleteFriend(idUser, idFriend));
        }
        userDBStorage.updateUser(user);
        /* User user1 = inMemoryUserStorage.getUserById(idUser);
        User user2 = inMemoryUserStorage.getUserById(idFriend);
        user1.delFriend(idFriend);
        user2.delFriend(idUser);*/
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
        /*User user1 = inMemoryUserStorage.getUserById(idUser1);
        User user2 = inMemoryUserStorage.getUserById(idUser2);
        Set<Long> set = new HashSet<>(user1.getFriends());
        set.retainAll(user2.getFriends());
        ArrayList<User> largeFriends = new ArrayList<>();
        for (long part : set) {
            User user = inMemoryUserStorage.getUserById((int) part);
            largeFriends.add(user);
        }
        return largeFriends;*/
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
