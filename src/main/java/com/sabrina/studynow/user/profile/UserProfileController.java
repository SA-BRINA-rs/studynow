package com.sabrina.studynow.user.profile;

import com.sabrina.studynow.token.APIToken;
import com.sabrina.studynow.token.APITokenService;
import com.sabrina.studynow.user.User;
import com.sabrina.studynow.user.UserRole;
import com.sabrina.studynow.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping(path = "profile")
public class UserProfileController {

    private final UserService userService;
    private final APITokenService apiTokenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserProfileController(
            UserService userService,
            APITokenService apiTokenService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.apiTokenService = apiTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    String profile(Model model, @AuthenticationPrincipal User user) {

        model.addAttribute("user", user);
        model.addAttribute("pageName", "profile");
        APIToken apiToken = apiTokenService.getAPITokenByUserId(user.getId());
        if (!apiToken.isActive()){
            apiToken.setId("");
        }
        model.addAttribute("apiToken", apiToken);

        return "profile";
    }

    @PostMapping
    String updateProfile(
            Model model,
            User user,
            @RequestParam("passwordRepeat") String passwordConfirm,
            @RequestParam(value="tokenStatus", required = false, defaultValue = "false") String tokenStatus,
            @AuthenticationPrincipal User userSession) {

        model.addAttribute("user", user);
        model.addAttribute("pageName", "profile");
        user.setId(userSession.getId());
        user.setEmail(userSession.getEmail());
        user.setUserRole(userSession.getUserRole());

        if(!user.getPassword().isBlank() && user.getPassword().equals(passwordConfirm)) {
            user.setPassword(passwordEncoder.encode(userSession.getPassword()));
        } else if (user.getPassword().isBlank()) {
            user.setPassword(userSession.getPassword());
        } else {
            model.addAttribute("error", "Passwords do not match");
            return "profile";
        }

        if (user.getUserRole().compareTo(UserRole.STUDENT) != 0) {
            APIToken apiToken = apiTokenService.getAPITokenByUserId(user.getId());
            apiToken.setActive(tokenStatus.equalsIgnoreCase("on"));
            apiTokenService.update(apiToken);
        }

        if(Objects.nonNull(userService.update(user))) {
            userSession.setPassword(user.getPassword());
            userSession.setFirstName(user.getFirstName());
            userSession.setLastName(user.getLastName());
            userSession.setUserRole(user.getUserRole());
        }
        return "redirect:/profile";
    }

}
