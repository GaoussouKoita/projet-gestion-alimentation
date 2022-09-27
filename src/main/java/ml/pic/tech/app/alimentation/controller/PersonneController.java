package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Personne;
import ml.pic.tech.app.alimentation.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client-fournisseur")
public class PersonneController {

    @Autowired
    private PersonneService service;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("personne", new Personne());
        return "personne/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("personne") Personne personne, Model model) {
        service.ajout(personne);
        all(model);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        model.addAttribute("personne", service.lecture(id));
        all(model);
        return "personne/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("personne", service.lecture(id));
        return "personne/search";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        model.addAttribute("personnes", service.liste());
        return "personne/liste";
    }
}
