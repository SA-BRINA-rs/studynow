package com.sabrina.studynow.view;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicPagesView {
    @RequestMapping(value = {"/", "index"})
    @GetMapping
    String index(Model model) {
        addAttributes(model, "home");
        return "index";
    }

    @RequestMapping(value = {"login"})
    @GetMapping
    String login(Model model) {
        addAttributes(model, "login");
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
