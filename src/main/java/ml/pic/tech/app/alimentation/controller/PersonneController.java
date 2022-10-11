package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Personne;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.PersonneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/client-fournisseur")
public class PersonneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private PersonneService service;
    @Autowired
    private AccountService userService;

    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire Client-Fournisseur");
        model.addAttribute("personne", new Personne());
        model.addAttribute("user", userService.currentUtilisateur());
        return "personne/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("personne") @Valid Personne personne, Errors errors, Model model) {
        LOGGER.info("Ajout de Client-Fournisseur dans la bd");
        model.addAttribute("user", userService.currentUtilisateur());
        if (errors.hasErrors()) {
            return "personne/ajout";

        } else {
        service.ajout(personne);
        }
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Client-Fournisseur");
        model.addAttribute("personne", service.lecture(id));
        model.addAttribute("user", userService.currentUtilisateur());
        return "personne/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Client-Fournisseur");
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("personne", service.lecture(id));
        return "personne/search";
    }

    @GetMapping("/liste")
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Client-Fournisseurs");
        model.addAttribute("personnes", service.liste(page).getContent());
        model.addAttribute("totalElements", service.liste(page).getTotalElements());
        model.addAttribute("pages", new int[ service.liste(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "personne/liste";
    }
}
