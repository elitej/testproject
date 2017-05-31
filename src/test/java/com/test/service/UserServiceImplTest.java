package com.test.service;

import com.test.model.Role;
import com.test.model.User;
import com.test.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
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
                .build();
        user1.setId(1L);
        Role roleUser = new Role("USER");
        User user2 = User.builder()
                .login("user")
                .password("user")
                .email("user@mail.ru")
                .role(roleUser)
                .build();
        user2.setId(2L);
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

        userService.save(user);

        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }
}