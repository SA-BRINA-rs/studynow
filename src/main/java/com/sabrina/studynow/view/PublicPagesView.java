package com.sabrina.studynow.view;

import com.sabrina.studynow.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicPagesView {

    @RequestMapping(value = {"/", "index"})
    @GetMapping
    String index(Model model, @AuthenticationPrincipal User user) {
        SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", user);
        model.addAttribute("pageName", "home");
        return "index";
    }

    @RequestMapping(value = {"login"})
    @GetMapping
    String login(Model model, @AuthenticationPrincipal User user) {
        SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", user);
        model.addAttribute("pageName", "login");
        return "login";
    }

    private void addAttributes(Model model, String pageName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
        }
        model.addAttribute("pageName", pageName);
    }

}
