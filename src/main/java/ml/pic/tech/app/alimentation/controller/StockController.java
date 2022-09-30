package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.service.MagasinService;
import ml.pic.tech.app.alimentation.service.ProduitService;
import ml.pic.tech.app.alimentation.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/stock")
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private StockService service;
    @Autowired
    private MagasinService magasinService;
    @Autowired
    private ProduitService produitService;

    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire Stock");
        model.addAttribute("stock", new Stock());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        return "stock/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("stock") @Valid Stock Stock, Errors errors, Model model) {
        LOGGER.info("Ajout de Stock dans la bd");
        if (errors.hasErrors()) {
            model.addAttribute("produits", produitService.liste());
            model.addAttribute("magasins", magasinService.liste());
            return "stock/ajout";
        }
        service.ajout(Stock);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Stock");
        model.addAttribute("stock", service.lecture(id));
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        return "stock/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Stock");
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
        LOGGER.info("Lister Stock");
        model.addAttribute("stocks", service.liste());
        return "stock/liste";
    }
}