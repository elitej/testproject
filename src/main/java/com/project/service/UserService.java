package com.project.service;


import com.project.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    void update(User user);

    User findByEmail(String email);

    List<User> findAll();

}
