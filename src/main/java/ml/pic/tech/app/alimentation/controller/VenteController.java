package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Vente;
import ml.pic.tech.app.alimentation.service.ProduitService;
import ml.pic.tech.app.alimentation.service.UserService;
import ml.pic.tech.app.alimentation.service.VenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/vente")
public class VenteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private VenteService service;
    @Autowired
    private UserService userService;
    @Autowired
    private ProduitService produitService;

    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire Vente");
        model.addAttribute("vente", new Vente());
        model.addAttribute("userId", userService.findCurrentUser().getId());
        model.addAttribute("produits", produitService.liste());
        return "vente/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("vente") @Valid Vente vente, Errors errors, Model model) {
        LOGGER.info("Ajout de Vente dans la bd");
        if (errors.hasErrors()) {
            model.addAttribute("userId", userService.findCurrentUser().getId());
            model.addAttribute("produits", produitService.liste());
            return "vente/ajout";
        }
        service.ajout(vente);

        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Vente");
        model.addAttribute("vente", service.lecture(id));
        model.addAttribute("userId", userService.findCurrentUser().getId());
        model.addAttribute("produits", produitService.liste());
        return "vente/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Vente");
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("vente", service.lecture(id));
        return "vente/search";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        LOGGER.info("Lister Ventes");
        model.addAttribute("ventes", service.liste());
        return "vente/liste";
    }
}