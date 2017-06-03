package com.example.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
public class MainController extends WebMvcConfigurerAdapter {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String redirectToHomePage(Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            if (role.equals("ADMIN")) {
                return "redirect:/admin";
            }
        }
        return "redirect:/user";
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String showLoginPage() {
        return "index";
    }
}
