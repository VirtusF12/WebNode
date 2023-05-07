package com.platform.WebNode.service;

import com.platform.WebNode.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IUserService {

    void createUser(User user);
    User getUserById(int id);
    void updateUserById(int id, String username, String password, LocalDate dob);
    void deleteUserById(Long id);

    Set<User> getUsers();
}
