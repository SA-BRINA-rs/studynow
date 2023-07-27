package com.sabrina.studynow.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping(path = "signup")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignUp(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("pageName", "signup");
        }
        return "signup";
    }

    @PostMapping(params = "userType")
    public ModelAndView postSignUp(
            Model model,
            @ModelAttribute User user,
            @RequestParam("passwordRepeat") String passwordConfirm,
            @RequestParam("userType") String userType) {

        if(Objects.nonNull(userType) && userType.equalsIgnoreCase(UserRole.INSTITUTION.toString())) {
            user.setUserRole(UserRole.INSTITUTION);
        }

        userService.add(user);
        return new ModelAndView("redirect:/login", model.asMap());
    }

}
