package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Categorie;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.CategorieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/categorie")
public class CategorieController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private CategorieService service;
    @Autowired
    private AccountService userService;

    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire Categorie");
        model.addAttribute("categorie", new Categorie());
        model.addAttribute("user", userService.currentUtilisateur());

        return "categorie/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("categorie") @Valid  Categorie categorie, Errors errors, Model model) {
        LOGGER.info("Ajout de Categorie dans la bd");
        model.addAttribute("user", userService.currentUtilisateur());

        if (errors.hasErrors()) {

            return "categorie/ajout";
        } else {
        service.ajout(categorie);}
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Mise a jour de Categorie");
        model.addAttribute("categorie", service.lecture(id));
        model.addAttribute("user", userService.currentUtilisateur());
        return "categorie/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Categorie");
        service.suppression(id);
        return "redirect:liste";
    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("categorie", service.lecture(id));
        return "categorie/search";
    }

    @GetMapping("/liste")
    public String all(Model model, @RequestParam(defaultValue = "0")int page) {
        LOGGER.info("Lister Categories");
        model.addAttribute("categories", service.liste(page).getContent());
        model.addAttribute("totalElements", service.liste(page).getTotalElements());
        model.addAttribute("pages", new int[ service.liste(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());

        return "categorie/liste";
    }
}