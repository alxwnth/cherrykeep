package my.nxvembrx.cherrybox.cherrykeep.controller;

import my.nxvembrx.cherrybox.cherrykeep.entity.User;
import my.nxvembrx.cherrybox.cherrykeep.service.UserSecurityService;
import my.nxvembrx.cherrybox.cherrykeep.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final UserSecurityService userSecurityService;

    public RegistrationController(UserService userService, UserSecurityService userSecurityService) {
        this.userService = userService;
        this.userSecurityService = userSecurityService;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userForm", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "signup";
        }
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords don't match");
            return "signup";
        }
        String passwordForAutologin = userForm.getPassword();
        if (userService.saveUser(userForm)) {
            try {
                userSecurityService.autoLogin(request, userForm.getUsername(), passwordForAutologin);
            } catch (ServletException e) {
                model.addAttribute("loginError", "Signed up but couldn't log in. Try logging in manually!");
                return "signup";
            }
            return "redirect:/login";
        } else {
            model.addAttribute("usernameError", "Username taken");
            return "signup";
        }
    }

}
