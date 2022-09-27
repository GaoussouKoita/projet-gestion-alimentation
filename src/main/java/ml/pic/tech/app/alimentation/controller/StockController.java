package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.service.MagasinService;
import ml.pic.tech.app.alimentation.service.ProduitService;
import ml.pic.tech.app.alimentation.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService service;
    @Autowired
    private MagasinService magasinService;
    @Autowired
    private ProduitService produitService;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("stock", new Stock());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        return "stock/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("stock") Stock Stock, Model model) {
        service.ajout(Stock);
        all(model);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        model.addAttribute("stock", service.lecture(id));
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        all(model);
        return "stock/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("stock", service.lecture(id));
        return "stock/search";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        model.addAttribute("stocks", service.liste());
        return "stock/liste";
    }
}