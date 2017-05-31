package com.project.web;

import com.project.model.User;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @RequestMapping(path = {"/admin/add", "/admin/edit"}, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newUser", new User());
            model.addAttribute("userList", userService.findAll());
            model.addAttribute("errorList", bindingResult.getAllErrors());
            return "admin";
        }

        userService.save(user);
        return "redirect:/admin";
    }
}
