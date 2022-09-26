package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Categorie;
import ml.pic.tech.app.alimentation.domaine.DetteClient;
import ml.pic.tech.app.alimentation.service.DetteClientService;
import ml.pic.tech.app.alimentation.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/detteClient")
public class DetteClientController {

    @Autowired
    private DetteClientService service;
    @Autowired
    private PersonneService personneService;

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("detteClient", new DetteClient());
        model.addAttribute("personnes", personneService.liste());
        return "detteClient/ajout";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute("detteClient") DetteClient detteClient, Model model){
        service.ajout(detteClient);
        all(model);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String  modifier(@RequestParam("id")Long id, Model model){
        model.addAttribute("detteClient", service.lecture(id));
        model.addAttribute("personnes", personneService.liste());
        all(model);
        return "detteClient/ajout";
    }

    @GetMapping("/delete")
    public String  delete(@RequestParam("id")Long id){
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id")Long id, Model model){
        model.addAttribute("detteClient", service.lecture(id));
        return "detteClient/search";
    }

    @GetMapping("/liste")
    public String all(Model model){
        model.addAttribute("detteClients", service.liste());
        return "detteClient/liste";
    }
}
