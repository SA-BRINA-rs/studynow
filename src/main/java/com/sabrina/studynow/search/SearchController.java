package com.sabrina.studynow.search;

import com.sabrina.studynow.institution.Institution;
import com.sabrina.studynow.institution.InstitutionService;
import com.sabrina.studynow.institution.card.InstitutionCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {

    private final InstitutionService institutionService;

    @Autowired
    public SearchController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @RequestMapping(value = {"search"})
    @GetMapping
    String searchInstitution(Model model) {
        List<InstitutionCard> cards = Optional.ofNullable(institutionService.getAllCards())
                .orElse(Collections.emptyList());
        System.out.println(cards.size() + " cards");
        model.addAttribute("cards", cards);
        addAttributes(model, "institution");
        return "search";
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
