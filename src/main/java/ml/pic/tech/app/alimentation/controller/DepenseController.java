package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Depense;
import ml.pic.tech.app.alimentation.service.DepenseService;
import ml.pic.tech.app.alimentation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/depense")
public class DepenseController {

    @Autowired
    private DepenseService service;
    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("depense", new Depense());
        model.addAttribute("users", userService.liste());
        return "depense/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("depense") Depense depense, Model model) {
        service.ajout(depense);
        all(model);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        model.addAttribute("depense", service.lecture(id));
        model.addAttribute("users", userService.liste());
        all(model);
        return "depense/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("depense", service.lecture(id));
        return "depense/search";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        model.addAttribute("depenses", service.liste());
        return "depense/liste";
    }
}
