package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validate.ValidException;


import java.util.*;

@Slf4j
@Service
public class UserService {
    private final Map<Integer, User> users = new HashMap<>();
    private int userId = 0;

    public void addUser(User user) throws ValidException {
        checkUser(user);
        user.setId(++userId);
        users.put(user.getId(), user);
    }

    public void updateUser(User user) throws ValidException {
        checkUser(user);
        users.put(user.getId(), user);
    }

    public List<User> getAllUsers() {
        return List.copyOf(users.values());
    }

    public User getUserById(int userId) {
        return users.get(userId);
    }

    public void addFriend(int userId, int friendId) {
        log.info("Пользователь {} добавил {} в список друзей.", userId, friendId);
        User user1 = users.get(userId);
        User user2 = users.get(friendId);
        user1.addFriend(friendId);
        user2.addFriend(userId);
    }


    public void delFriend(int userId, int friendId) {
        log.info("Пользователь {} удалил {} из списка друзей.", userId, friendId);
        User user1 = users.get(userId);
        User user2 = users.get(friendId);
        user1.delFriend(friendId);
        user2.delFriend(userId);
    }

    public ArrayList<User> getFriends(int userId) {
        User user = users.get(userId);
        ArrayList<User> friends = new ArrayList<>();
        for (long element : user.getFriends()) {
            User listUser = getUserById((int) element);
            friends.add(listUser);
        }
        return friends;
    }

    public ArrayList<User> getMutualFriends(int firstUserId, int secondUserId) {
        User user1 = users.get(firstUserId);
        User user2 = users.get(secondUserId);
        Set<Long> commonElements = new HashSet<>(user1.getFriends());
        commonElements.retainAll(user2.getFriends());
        ArrayList<User> mutualFriends = new ArrayList<>();
        for (long element : commonElements) {
            User user = users.get((int) element);
            mutualFriends.add(user);
        }
        return mutualFriends;
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
