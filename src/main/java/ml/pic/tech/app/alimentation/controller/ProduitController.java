package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
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
    @Autowired
    private AccountService userService;
    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire Produit");
        model.addAttribute("produit", new Produit());
        model.addAttribute("categories", categorieService.liste());
        model.addAttribute("user", userService.currentUtilisateur());
        return "produit/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("produit") @Valid Produit produit, Errors errors, Model model) {
        LOGGER.info("Ajout de Produit dans la bd");
        model.addAttribute("categories", categorieService.liste());
        model.addAttribute("user", userService.currentUtilisateur());
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
        model.addAttribute("user", userService.currentUtilisateur());
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
        model.addAttribute("user", userService.currentUtilisateur());


        model.addAttribute("produits", service.rechParNom(nom));
        model.addAttribute("totalElements", service.rechParNom(nom).getTotalElements());
        model.addAttribute("pages", new int[ service.rechParNom(nom).getTotalPages()]);
//        model.addAttribute("currentPage", page);
        return "produit/liste";
    }

    @GetMapping("/liste")
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Produits");
        model.addAttribute("produits", service.liste(page).getContent());
        model.addAttribute("totalElements", service.liste(page).getTotalElements());
        model.addAttribute("pages", new int[ service.liste(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "produit/liste";
    }
}
