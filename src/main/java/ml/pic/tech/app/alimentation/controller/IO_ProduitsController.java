package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.IO_Produits;
import ml.pic.tech.app.alimentation.service.IO_ProduitsService;
import ml.pic.tech.app.alimentation.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IO_ProduitsController {

    @Autowired
    private IO_ProduitsService service;
    @Autowired
    private ProduitService produitService;

    @GetMapping("/io_produits/add")
    public String addForm(Model model) {
        model.addAttribute("io_produits", new IO_Produits());
        model.addAttribute("produits", produitService.liste());
        return "io_produits/ajout";
    }

    @PostMapping("/io_produits/add")
    public String add(@ModelAttribute("io_produits") IO_Produits io_produits, Model model) {
        service.ajout(io_produits);
        return "io_produits/ajout";
    }
}