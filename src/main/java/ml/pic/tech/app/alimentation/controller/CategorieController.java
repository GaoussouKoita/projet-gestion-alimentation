package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Categorie;
import ml.pic.tech.app.alimentation.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorie")
public class CategorieController {

    @Autowired
    private CategorieService service;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("categorie", new Categorie());
        return "categorie/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("categorie") Categorie categorie, Model model) {
        service.ajout(categorie);
        all(model);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        model.addAttribute("categorie", service.lecture(id));
        all(model);
        return "categorie/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("categorie", service.lecture(id));
        return "categorie/search";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        model.addAttribute("categories", service.liste());
        return "categorie/liste";
    }
}