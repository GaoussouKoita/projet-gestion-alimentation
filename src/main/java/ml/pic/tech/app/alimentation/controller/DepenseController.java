package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Depense;
import ml.pic.tech.app.alimentation.service.DepenseService;
import ml.pic.tech.app.alimentation.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/depense")
public class DepenseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private DepenseService service;
    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire Depense");
        model.addAttribute("depense", new Depense());
        model.addAttribute("userId", userService.findCurrentUser().getId());
        return "depense/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("depense") @Valid Depense depense, Errors errors, Model model) {
        LOGGER.info("Ajout de Depense dans la bd");
        if (errors.hasErrors()) {
            model.addAttribute("userId", userService.findCurrentUser().getId());
            return "depense/ajout";
        } else {
        service.ajout(depense);}
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Depense");
        model.addAttribute("depense", service.lecture(id));
        model.addAttribute("userId", userService.findCurrentUser().getId());
        return "depense/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Depense");
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("depense", service.lecture(id));
        return "depense/search";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        LOGGER.info("Lister Depenses");
        model.addAttribute("depenses", service.liste());
        return "depense/liste";
    }
}
