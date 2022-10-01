package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.TransfertStock;
import ml.pic.tech.app.alimentation.service.MagasinService;
import ml.pic.tech.app.alimentation.service.ProduitService;
import ml.pic.tech.app.alimentation.service.TransfertStockService;
import ml.pic.tech.app.alimentation.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/transfertStock")
public class TransfertStockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

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
        LOGGER.info("Formulaire Approvission");
        model.addAttribute("transfertStock", new TransfertStock());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("userId", userService.findCurrentUser().getId());
        return "transfertStock/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("transfertStock") @Valid TransfertStock transfertStock, Errors errors, Model model) {

        LOGGER.info("Ajout d'Approvission dans la bd");
        if (errors.hasErrors()) {
            model.addAttribute("produits", produitService.liste());
            model.addAttribute("magasins", magasinService.liste());
            model.addAttribute("userId", userService.findCurrentUser().getId());
            return "transfertStock/ajout";
        }
        service.ajout(transfertStock);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update d'Approvission");
        model.addAttribute("transfertStock", service.lecture(id));
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("userId", userService.findCurrentUser().getId());
        return "transfertStock/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression d'Approvission");
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("transfertStock", service.lecture(id));
        return "transfertStock/search";
    }

    @GetMapping("/liste")
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Approvission");
        model.addAttribute("transfertStocks", service.liste(page).getContent());
        model.addAttribute("totalElements", service.liste(page).getTotalElements());
        model.addAttribute("pages", new int[ service.liste(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "transfertStock/liste";
    }
}