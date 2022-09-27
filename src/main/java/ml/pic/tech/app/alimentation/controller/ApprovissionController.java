package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Approvission;
import ml.pic.tech.app.alimentation.service.ApprovissionService;
import ml.pic.tech.app.alimentation.service.PersonneService;
import ml.pic.tech.app.alimentation.service.ProduitService;
import ml.pic.tech.app.alimentation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/approvission")
public class ApprovissionController {

    @Autowired
    private ApprovissionService service;
    @Autowired
    private PersonneService personneService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProduitService produitService;


    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("approvission", new Approvission());
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("users", userService.liste());
        model.addAttribute("produits", produitService.liste());
        return "approvission/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("approvission") Approvission approvission, Model model) {
        service.ajout(approvission);
        all(model);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        model.addAttribute("approvission", service.lecture(id));
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("users", userService.liste());
        all(model);
        return "approvission/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("approvission", service.lecture(id));
        return "approvission/search";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        model.addAttribute("approvissions", service.liste());
        return "approvission/liste";
    }
}
