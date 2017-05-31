package com.test.service;


import com.test.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    void update(User user);

    User findByEmail(String email);

    List<User> findAll();

}
