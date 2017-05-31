package com.project.web;

import com.project.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

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

    @RequestMapping(path = "/index")
    public String showLoginPage(@RequestParam(name = "error", required = false) String error,
                                @RequestParam(name = "logout", required = false) String logout,
                                Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        model.addAttribute("user", new User());
        return "index";
    }
}
