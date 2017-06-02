package com.example.web;

import com.example.exception.UniqueViolationException;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("userList", userService.findAll());
        model.addAttribute("formUser", new User());
        return "admin";
    }

    @RequestMapping(path = "/admin/add", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("formUser") @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userList", userService.findAll());
            return "admin";
        }

        userService.save(user);
        return "redirect:/admin";
    }

    @RequestMapping(path = "/admin/edit", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("formUser") @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userList", userService.findAll());
            return "admin";
        }

        userService.update(user);
        return "redirect:/admin";
    }

    @ExceptionHandler(value = UniqueViolationException.class)
    public String handleUniqueViolation(UniqueViolationException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
