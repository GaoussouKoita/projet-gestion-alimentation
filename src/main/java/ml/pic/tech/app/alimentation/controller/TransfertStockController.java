package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.TransfertStock;
import ml.pic.tech.app.alimentation.service.MagasinService;
import ml.pic.tech.app.alimentation.service.ProduitService;
import ml.pic.tech.app.alimentation.service.TransfertStockService;
import ml.pic.tech.app.alimentation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transfertStock")
public class TransfertStockController {

    @Autowired
    private TransfertStockService service;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private MagasinService magasinService;
    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("transfertStock", new TransfertStock());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("users", userService.liste());
        return "transfertStock/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("transfertStock") TransfertStock transfertStock, Model model) {
        try {
            service.ajout(transfertStock);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        all(model);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        model.addAttribute("transfertStock", service.lecture(id));
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("users", userService.liste());
        all(model);
        return "transfertStock/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("transfertStock", service.lecture(id));
        return "transfertStock/search";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        model.addAttribute("transfertStocks", service.liste());
        return "transfertStock/liste";
    }
}