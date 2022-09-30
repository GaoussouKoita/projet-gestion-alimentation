package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.DetteClient;
import ml.pic.tech.app.alimentation.service.DetteClientService;
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
@RequestMapping("/detteClient")
public class DetteClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private DetteClientService service;
    @Autowired
    private PersonneService personneService;

    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire Dette");
        model.addAttribute("detteClient", new DetteClient());
        model.addAttribute("personnes", personneService.liste());
        return "detteClient/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("detteClient") @Valid DetteClient detteClient, Errors errors, Model model) {
        LOGGER.info("Ajout de Dette dans la bd");
        if (errors.hasErrors()) {
            model.addAttribute("personnes", personneService.liste());
            return "detteClient/ajout";
        } else {
        service.ajout(detteClient);}
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Dette");
        model.addAttribute("detteClient", service.lecture(id));
        model.addAttribute("personnes", personneService.liste());
        return "detteClient/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Dette");
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("detteClient", service.lecture(id));
        return "detteClient/search";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        LOGGER.info("Lister Dettes");
        model.addAttribute("detteClients", service.liste());
        return "detteClient/liste";
    }
}
