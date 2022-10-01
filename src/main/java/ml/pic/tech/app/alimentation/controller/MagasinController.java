package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Magasin;
import ml.pic.tech.app.alimentation.service.MagasinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/magasin")
public class MagasinController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private MagasinService service;

    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire Magasin");
        model.addAttribute("magasin", new Magasin());
        return "magasin/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("magasin") @Valid Magasin magasin, Errors errors, Model model) {
        LOGGER.info("Ajout de Magasin dans la bd");
        if (errors.hasErrors()) {
            return "magasin/ajout";
        } else {
            service.ajout(magasin);
        }
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Magasin");
        model.addAttribute("magasin", service.lecture(id));
        return "magasin/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Magasin");
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("magasin", service.lecture(id));
        return "magasin/search";
    }

    @GetMapping("/liste")
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Magasins");
        model.addAttribute("magasins", service.liste(page).getContent());
        model.addAttribute("totalElements", service.liste(page).getTotalElements());
        model.addAttribute("pages", new int[ service.liste(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "magasin/liste";
    }
}
