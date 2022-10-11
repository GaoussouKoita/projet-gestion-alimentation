package ml.pic.tech.app.alimentation.securite.controller;

import ml.pic.tech.app.alimentation.securite.entity.ChangePassword;
import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
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
public class UtilisateurController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private AccountService accountService;


    @GetMapping("/add")
    public String addForm(Model model) {
        LOGGER.info("Formulaire user");
        model.addAttribute("userNew", new Utilisateur());
        model.addAttribute("roles", accountService.roleList());
        model.addAttribute("user", accountService.currentUtilisateur());
        return "user/ajout";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("userNew") @Valid Utilisateur utilisateur, Errors errors, Model model) {
        LOGGER.info("Ajout user dans la bd");
        model.addAttribute("user", accountService.currentUtilisateur());
        if (errors.hasErrors()) {

            model.addAttribute("roles", accountService.roleList());
            return "user/ajout";

        } else {
            accountService.addUtilisateur(utilisateur);
        }

        return "redirect:liste";
    }

    @GetMapping("/update")
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update user");
        model.addAttribute("user", accountService.currentUtilisateur());
        model.addAttribute("userNew", accountService.lecture(id));
        model.addAttribute("userRoles", accountService.lecture(id).getRoles());
        model.addAttribute("roles", accountService.roleList());
        return "user/ajout";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.warn("Suppression user");
        accountService.suppression(id);
        return "redirect:liste";

    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("userNew", accountService.lecture(id));
        return "user/search";
    }

    @GetMapping("/liste")
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister users");
        model.addAttribute("users", accountService.utilisateurListPage(page).getContent());
        model.addAttribute("totalElements", accountService.utilisateurListPage(page).getTotalElements());
        model.addAttribute("pages", new int[accountService.utilisateurListPage(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("user", accountService.currentUtilisateur());
        return "user/liste";
    }


    @GetMapping("/password")
    public String changePasswordForm(Model model) {
        model.addAttribute("changePassword", new ChangePassword());
        model.addAttribute("user", accountService.currentUtilisateur());
        return "user/password";
    }

    @PostMapping("/password")
    public String PasswordForm(@ModelAttribute("changePassword") @Valid ChangePassword changePassword, Errors errors, Model model) {
        model.addAttribute("user", accountService.currentUtilisateur());

        Utilisateur user = accountService.currentUtilisateur();
        String email = user.getLogin();
        String oldPassword = changePassword.getOldPassword();
        String newPassword = changePassword.getNewPassword();
        String confirmation = changePassword.getConfirmation();

        if (accountService.passwordEncodeVerifie(oldPassword, user.getPassword())) {
            if (newPassword.equals(confirmation)) {
                accountService.updatePassword(email, newPassword);
                return "redirect:/";
            } else {
                errors.rejectValue("newPassword", "",
                        "Le password et la confirmation sont different");
            }

        } else {
            errors.rejectValue("oldPassword", "",
                    "L'ancien password est incorrect");

        }

        if (errors.hasErrors()) {
            return "user/password";
        }
        return "user/password";    }


}
