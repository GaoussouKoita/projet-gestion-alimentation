package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.User;
import ml.pic.tech.app.alimentation.securite.AccontService;
import ml.pic.tech.app.alimentation.securite.UserRole;
import ml.pic.tech.app.alimentation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private AccontService accontService;


    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        return "user/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") User user, Model model) {
        accontService.addUser(user);
        all(model);
        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", service.lecture(id));
        model.addAttribute("userRoles", service.lecture(id).getRoles());

        all(model);
        return "user/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", service.lecture(id));
        return "user/search";
    }

    @GetMapping("/liste")
    public String all(Model model) {
        model.addAttribute("users", service.liste());
        return "user/liste";
    }

    @GetMapping("/addRoleToUSer")
    public String addRToU(Model model) {
        model.addAttribute("userRole", new UserRole());
        model.addAttribute("users", accontService.allUsers());
        model.addAttribute("roles", accontService.allRoles());
        return "user/addRoleToUser";
    }

    @PostMapping("/addRoleToUser")
    public String addRoletoU(@ModelAttribute("userRoles") UserRole userRole) {
        accontService.addRoleToUser(userRole.getLogin(), userRole.getRoleName());
        return "redirect:liste";
    }
}
