package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.domain.User;

public interface UserService {
    User createUser(User user);

    User getUserById(Integer id);

    List<User> getAllUsers();

    User updateUser(Integer id, User user);

    void deleteUser(Integer id);
}
