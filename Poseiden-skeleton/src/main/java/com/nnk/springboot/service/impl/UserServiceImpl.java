package com.nnk.springboot.service.impl;

import java.util.List;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Integer id, User user) {
        User userToUpdate = userRepository.findById(id).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setFullname(user.getFullname());
            userToUpdate.setRole(user.getRole());
            userToUpdate.setPassword(user.getPassword());
            return userRepository.save(userToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
