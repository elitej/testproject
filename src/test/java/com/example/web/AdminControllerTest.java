package com.example.web;

import com.example.exception.UniqueViolationException;
import com.example.model.Role;
import com.example.model.User;
import com.example.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void showAdminPage_ShouldAddUsersToModelAndRenderAdminPage() throws Exception {
        Role role = new Role("ADMIN");
        User user = User.builder()
                .login("admin")
                .password("admin")
                .email("admin@mail.ru")
                .role(role)
                .id(1L)
                .build();
        when(userService.findAll()).thenReturn(Arrays.asList(user));


        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attribute("userList", hasSize(1)))
                .andExpect(model().attribute("userList", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("login", is("admin")),
                                hasProperty("password", is("admin")),
                                hasProperty("email", is("admin@mail.ru")),
                                hasProperty("role", is(role))))));

        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void saveUser_ShouldSaveUserAndRedirectToAdmin() throws Exception {
        Role role = new Role("ADMIN");
        User user = User.builder()
                .login("log")
                .password("pas")
                .email("email@mail.ru")
                .role(role)
                .build();

        mockMvc.perform(
                post("/admin/add")
                        .param("email", "email@mail.ru")
                        .param("login", "log")
                        .param("password", "pas")
                        .param("role.name", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));

        verify(userService, times(1)).save(user);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void handleUniqueViolation_ShouldRenderErrorPage_WhenPassedUserWitnNotUniqueFields() throws Exception {
        Role role = new Role("ADMIN");
        User user = User.builder()
                .login("log")
                .password("pas")
                .email("email@mail.ru")
                .role(role)
                .build();
        doThrow(UniqueViolationException.class).when(userService).save(user);

        mockMvc.perform(
                post("/admin/add")
                        .param("email", "email@mail.ru")
                        .param("login", "log")
                        .param("password", "pas")
                        .param("role.name", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }
}