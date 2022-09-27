package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Vente;
import ml.pic.tech.app.alimentation.service.ProduitService;
import ml.pic.tech.app.alimentation.service.UserService;
import ml.pic.tech.app.alimentation.service.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vente")
public class VenteController {

    @Autowired
    private VenteService service;
    @Autowired
    private UserService userService;
    @Autowired
    private ProduitService produitService;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("vente", new Vente());
        model.addAttribute("users", userService.liste());
        model.addAttribute("produits", produitService.liste());


        return "vente/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("vente") Vente vente, Model model) {
        service.ajout(vente);
        all(model);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        model.addAttribute("vente", service.lecture(id));
        model.addAttribute("users", userService.liste());
        model.addAttribute("produits", produitService.liste());
        all(model);
        return "vente/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
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
        model.addAttribute("ventes", service.liste());
        return "vente/liste";
    }
}