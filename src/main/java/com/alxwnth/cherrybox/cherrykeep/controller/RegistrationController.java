package com.alxwnth.cherrybox.cherrykeep.controller;

import com.alxwnth.cherrybox.cherrykeep.entity.User;
import com.alxwnth.cherrybox.cherrykeep.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userForm", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "signup";
        }
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords don't match");
            return "signup";
        }
        if (!userService.saveUser(userForm)) {
            model.addAttribute("usernameError", "Username taken");
        }

        return "redirect:/";
    }

}
