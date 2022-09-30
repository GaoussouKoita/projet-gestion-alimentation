package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.service.CategorieService;
import ml.pic.tech.app.alimentation.service.ProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/produit")
public class ProduitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private ProduitService service;
    @Autowired
    private CategorieService categorieService;

    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire Produit");
        model.addAttribute("produit", new Produit());
        model.addAttribute("categories", categorieService.liste());
        return "produit/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("produit") @Valid Produit produit, Errors errors, Model model) {
        LOGGER.info("Ajout de Produit dans la bd");
        if (errors.hasErrors()) {
            model.addAttribute("categories", categorieService.liste());
            return "produit/ajout";
        } else {
            service.ajout(produit);
        }

        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Produit");
        model.addAttribute("produit", service.lecture(id));
        model.addAttribute("categories", categorieService.liste());
        return "produit/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Produit");
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("nom") String nom, Model model) {

        model.addAttribute("produits", service.rechParNom(nom));
        return "produit/liste";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        LOGGER.info("Lister Produits");
        model.addAttribute("produits", service.liste());
        return "produit/liste";
    }
}
