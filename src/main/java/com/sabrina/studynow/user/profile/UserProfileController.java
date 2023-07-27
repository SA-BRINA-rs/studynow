package com.sabrina.studynow.user.profile;

import com.sabrina.studynow.token.APIToken;
import com.sabrina.studynow.token.APITokenService;
import com.sabrina.studynow.user.User;
import com.sabrina.studynow.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserProfileController {

    private final UserService userService;
    private final APITokenService apiTokenService;

    @Autowired
    public UserProfileController(UserService userService, APITokenService apiTokenService) {
        this.userService = userService;
        this.apiTokenService = apiTokenService;
    }

    @RequestMapping(value = {"profile"})
    @GetMapping
    String profile(Model model, @AuthenticationPrincipal User user) {

        addAttributes(model, user, "profile");

        APIToken apiToken = apiTokenService.getAPIToken(user.getId());
        model.addAttribute("apiToken", apiToken);

        return "profile";
    }

    private void addAttributes(Model model, User user,String pageName) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("pageName", pageName);
    }

}
