package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserById(long id);

    public List<User> allUsers();

    public User getUserId(Long id);

    public void addUser(User user);

    public void updateUser(User user);

    public void deleteUser(Long id);

    public User getUserByUsername(String username);
}
