package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDBStorage;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceTest {

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testFindUserById() {
        User user = new User(
                "ya@ya.ru",
                "login1",
                "name1",
                LocalDate.of(1989, 2, 10));
        UserDBStorage userDBStorage = new UserDBStorage(jdbcTemplate);
        userDBStorage.addUser(user);
        User savedUser = userDBStorage.getUserById(user.getId());
        assertThat(savedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(user);
    }
}