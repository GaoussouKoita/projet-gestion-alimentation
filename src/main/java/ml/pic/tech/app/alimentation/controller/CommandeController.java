package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Commande;
import ml.pic.tech.app.alimentation.service.CommandeService;
import ml.pic.tech.app.alimentation.service.PersonneService;
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
@RequestMapping("/commande")
public class CommandeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private CommandeService service;
    @Autowired
    private PersonneService personneService;
    @Autowired
    private UserService userService;


    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire Commande");
        model.addAttribute("commande", new Commande());
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("userId", userService.findCurrentUser().getId());
        return "commande/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("commande") @Valid Commande commande, Errors errors, Model model) {
        LOGGER.info("Ajout de Commande dans la base");
        if (errors.hasErrors()) {
            model.addAttribute("personnes", personneService.liste());
            model.addAttribute("userId", userService.findCurrentUser().getId());
            return "commande/ajout";
        } else {
         service.ajout(commande);}
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Mise a jour de Commande");
        model.addAttribute("commande", service.lecture(id));
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("userId", userService.findCurrentUser().getId());
        return "commande/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Commande");
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("commande", service.lecture(id));
        return "commande/search";
    }

    @GetMapping("/liste")
    public String all(Model model, @RequestParam(defaultValue = "0")int page) {
        LOGGER.info("Lister Commandes");
        model.addAttribute("commandes", service.liste(page).getContent());
        model.addAttribute("totalElements", service.liste(page).getTotalElements());
        model.addAttribute("pages", new int[ service.liste(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "commande/liste";
    }
}
