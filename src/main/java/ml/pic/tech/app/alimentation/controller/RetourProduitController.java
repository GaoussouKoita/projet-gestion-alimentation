package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.RetourProduit;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/retour-produit")
public class RetourProduitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private RetourProduitService service;
    @Autowired
    private PersonneService personneService;
    @Autowired
    private AccountService userService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private MagasinService magasinService;
    @Autowired
    private VenteService venteService;


    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire retourProduit");
        model.addAttribute("retourProduit", new RetourProduit());
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("userId", userService.currentUtilisateur().getId());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("ventes", venteService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("user", userService.currentUtilisateur());
        return "retourProduit/ajout";

    }

    @PostMapping("/add")
    public String add(@ModelAttribute("retourProduit") @Valid RetourProduit retourProduit, Errors errors, Model model) {


        LOGGER.info("Ajout de retoutProduit dans la bd");
        model.addAttribute("user", userService.currentUtilisateur());

        if (errors.hasErrors()) {
            model.addAttribute("personnes", personneService.liste());
            model.addAttribute("userId", userService.currentUtilisateur().getId());
            model.addAttribute("produits", produitService.liste());
            model.addAttribute("ventes", venteService.liste());
            model.addAttribute("magasins", magasinService.liste());
            return "retourProduit/ajout";
        }
        service.ajout(retourProduit);

        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Mise a jour de Retour Produit");
        model.addAttribute("retourProduit", service.lecture(id));
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("ventes", venteService.liste());
        model.addAttribute("userId", userService.currentUtilisateur().getId());
        model.addAttribute("user", userService.currentUtilisateur());
        return "retourProduit/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression d'une Retour Produit");
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("retourProduit", service.lecture(id));
        model.addAttribute("user", userService.currentUtilisateur());
        return "retourProduit/search";
    }

    @GetMapping("/liste")
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister retourProduits");
        model.addAttribute("retourProduits", service.liste(page).getContent());
        model.addAttribute("totalElements", service.liste(page).getTotalElements());
        model.addAttribute("pages", new int[service.liste(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "retourProduit/liste";
    }
}
