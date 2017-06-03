package com.example.service;

import com.example.exception.UniqueViolationException;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        reset(userRepository);
    }

    @Test
    public void findByEmail_ShouldReturnUserWithTheSpecifiedEmail() {
        Role roleUser = new Role("USER");
        User user = User.builder()
                .login("user")
                .password("user")
                .email("user@mail.ru")
                .role(roleUser)
                .build();
        user.setId(2L);
        when(userRepository.findByEmail("user@mail.ru")).thenReturn(user);

        User returnedUser = userService.findByEmail("user@mail.ru");
        assertEquals(new Long(2), returnedUser.getId());
        assertEquals("user", returnedUser.getLogin());
        assertEquals("user", returnedUser.getPassword());
        assertEquals("user@mail.ru", returnedUser.getEmail());
        assertEquals("USER", returnedUser.getRole().getName());

        verify(userRepository, times(1)).findByEmail("user@mail.ru");
        verifyNoMoreInteractions(userRepository);

    }

    @Test
    public void findAll_ShouldReturnUsers() {
        Role roleAdmin = new Role("ADMIN");
        User user1 = User.builder()
                .login("admin")
                .password("admin")
                .email("admin@mail.ru")
                .role(roleAdmin)
                .id(1L)
                .build();
        Role roleUser = new Role("USER");
        User user2 = User.builder()
                .login("user")
                .password("user")
                .email("user@mail.ru")
                .role(roleUser)
                .id(2L)
                .build();
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> returnedUsers = userService.findAll();
        assertEquals(returnedUsers, users);

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void save_ShouldSaveSpecifiedUser() {
        Role roleAdmin = new Role("ADMIN");
        User user = User.builder()
                .login("admin")
                .password("admin")
                .email("admin@mail.ru")
                .role(roleAdmin)
                .build();
        when(userRepository.save(user)).thenReturn(user);

        userService.save(user);

        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).findByEmailOrLogin("admin@mail.ru", "admin");
        verifyNoMoreInteractions(userRepository);
    }

    @Test(expected = UniqueViolationException.class)
    public void save_ShouldThrowException_WhenPassedUserWitNotUniqueFields() {

        Role roleAdmin = new Role("ADMIN");
        User user1 = User.builder()
                .id(1L)
                .login("admin")
                .password("admin")
                .email("admin@mail.ru")
                .role(roleAdmin)
                .build();
        when(userRepository.findByEmailOrLogin("admin@mail.ru", "a")).thenReturn(Collections.singletonList(user1));

        User user2 = User.builder()
                .login("a")
                .password("admin")
                .email("admin@mail.ru")
                .role(roleAdmin)
                .build();
        userService.save(user2);
    }
}