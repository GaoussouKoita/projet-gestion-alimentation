package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Commande;
import ml.pic.tech.app.alimentation.service.CommandeService;
import ml.pic.tech.app.alimentation.service.PersonneService;
import ml.pic.tech.app.alimentation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/commande")
public class CommandeController {

    @Autowired
    private CommandeService service;
    @Autowired
    private PersonneService personneService;
    @Autowired
    private UserService userService;


    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("commande", new Commande());
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("users", userService.liste());

        return "commande/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("commande") Commande commande, Model model) {
        service.ajout(commande);
        all(model);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        model.addAttribute("commande", service.lecture(id));
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("users", userService.liste());
        all(model);
        return "commande/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("commande", service.lecture(id));
        return "commande/search";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        model.addAttribute("commandes", service.liste());
        return "commande/liste";
    }
}
