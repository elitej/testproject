package com.project.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegularUserController {

    @RequestMapping(path = "/user")
    public String showUserPage(Model model) {
        return "user";
    }

}

