package com.example.exception;

import com.example.model.Role;
import com.example.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;


public class ExceptionUtilTest {

    @Test(expected = UniqueViolationException.class)
    public void checkUserForUniqueEmailOrLogin_ShouldThrowException_WhenSaveNewUser() {
        Role role = new Role("USER");
        User newUser = User.builder().email("email@mail.ru").login("login")
                .password("password").role(role).build();
        User userInDb = User.builder().id(123L).email("email@mail.ru").login("anotherLogin")
                .password("pass").role(role).build();
        ExceptionUtil.checkUserForUniqueEmailOrLogin(newUser, Collections.singletonList(userInDb));
    }

    @Test
    public void checkUserForUniqueEmailOrLogin_ShouldComplectedNormally_WhenSaveNewUser() {
        Role role = new Role("USER");
        User newUser = User.builder().email("email@mail.ru").login("login")
                .password("password").role(role).build();
        ExceptionUtil.checkUserForUniqueEmailOrLogin(newUser, new ArrayList<>());
    }

    @Test(expected = UniqueViolationException.class)
    public void checkUserForUniqueEmailOrLogin_ShouldThrowException_WhenUpdateUnique() {
        Role role = new Role("USER");
        User newUser = User.builder().id(1L).email("another@mail.ru").login("login")
                .password("password").role(role).build();
        User userInDb = User.builder().id(123L).email("email@mail.ru").login("login")
                .password("pass").role(role).build();
        ExceptionUtil.checkUserForUniqueEmailOrLogin(newUser, Collections.singletonList(userInDb));
    }

    @Test
    public void checkUserForUniqueEmailOrLogin_ShouldComplectedNormally_WhenUpdateNotUniqueField() {
        Role role = new Role("USER");
        User newUser = User.builder().id(123L).email("email@mail.ru").login("login")
                .password("password").role(role).build();
        User userInDb = User.builder().id(123L).email("email@mail.ru").login("login")
                .password("pass").role(role).build();
        ExceptionUtil.checkUserForUniqueEmailOrLogin(newUser, Collections.singletonList(userInDb));
    }
}