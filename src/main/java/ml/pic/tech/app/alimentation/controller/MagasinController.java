package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Magasin;
import ml.pic.tech.app.alimentation.service.MagasinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/magasin")
public class MagasinController {

    @Autowired
    private MagasinService service;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("magasin", new Magasin());
        return "magasin/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("magasin") Magasin magasin, Model model) {
        service.ajout(magasin);
        all(model);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        model.addAttribute("magasin", service.lecture(id));
        all(model);
        return "magasin/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("magasin", service.lecture(id));
        return "magasin/search";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        model.addAttribute("magasins", service.liste());
        return "magasin/liste";
    }
}
