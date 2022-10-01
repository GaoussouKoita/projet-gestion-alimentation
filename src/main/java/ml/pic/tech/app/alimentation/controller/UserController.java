package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.User;
import ml.pic.tech.app.alimentation.securite.AccontService;
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
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private UserService service;

    @Autowired
    private AccontService accontService;


    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire Utilisateur");
        model.addAttribute("user", new User());
        model.addAttribute("roles", accontService.allRoles());
        return "user/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") @Valid User user, Errors errors, Model model) {
        LOGGER.info("Ajout Utilisateur dans la bd");
        if (errors.hasErrors()) {

            model.addAttribute("roles", accontService.allRoles());
            return "user/ajout";

        } else {
            accontService.addUser(user);
        }

        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update Utilisateur");
        model.addAttribute("user", service.lecture(id));
        model.addAttribute("userRoles", service.lecture(id).getRoles());
        model.addAttribute("roles", accontService.allRoles());
        return "user/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.warn("Suppression Utilisateur");
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", service.lecture(id));
        return "user/search";
    }

    @GetMapping("/liste")
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Utilisateurs");
        model.addAttribute("users", service.liste(page).getContent());
        model.addAttribute("totalElements", service.liste(page).getTotalElements());
        model.addAttribute("pages", new int[ service.liste(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "user/liste";
    }

}
