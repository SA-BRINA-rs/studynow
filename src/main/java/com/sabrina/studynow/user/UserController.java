package com.sabrina.studynow.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String getSignUp(Model model, @AuthenticationPrincipal User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", user);
        model.addAttribute("pageName", "signup");
        return "signup";
    }

    @PostMapping
    public ModelAndView postSignUp(Model model, @ModelAttribute User user,
            @RequestParam("passwordRepeat") String passwordConfirm) {

        userService.add(user);
        return new ModelAndView("redirect:/login", model.asMap());
    }

}
