package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.service.CategorieService;
import ml.pic.tech.app.alimentation.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produit")
public class ProduitController {

    @Autowired
    private ProduitService service;
    @Autowired
    private CategorieService categorieService;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("produit", new Produit());
        model.addAttribute("categories", categorieService.liste());
        return "produit/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("produit") Produit produit, Model model) {
        service.ajout(produit);
        all(model);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        model.addAttribute("produit", service.lecture(id));
        model.addAttribute("categories", categorieService.liste());

        all(model);
        return "produit/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
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
        model.addAttribute("produits", service.liste());
        return "produit/liste";
    }
}
