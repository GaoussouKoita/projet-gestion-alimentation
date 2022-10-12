package ml.pic.tech.app.alimentation.securite.controller;

import ml.pic.tech.app.alimentation.securite.entity.ChangePassword;
import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.utils.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(Endpoint.UTILISATEUR_ENDPOINT)
public class UtilisateurController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private AccountService accountService;


    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model) {
        LOGGER.info("Formulaire user");
        model.addAttribute("userNew", new Utilisateur());
        model.addAttribute("roles", accountService.roleList());
        model.addAttribute("user", accountService.currentUtilisateur());
        return "utilisateur/ajout";
    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("userNew") @Valid Utilisateur utilisateur, Errors errors, Model model) {
        LOGGER.info("Ajout user dans la bd");
        model.addAttribute("user", accountService.currentUtilisateur());
        if (errors.hasErrors()) {

            model.addAttribute("roles", accountService.roleList());
            return "utilisateur/ajout";

        } else {
            accountService.addUtilisateur(utilisateur);
        }

        return "redirect:liste";
    }

    @GetMapping(Endpoint.UPDATE_ENDPOINT)
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update user");
        model.addAttribute("user", accountService.currentUtilisateur());
        model.addAttribute("userNew", accountService.lecture(id));
        model.addAttribute("userRoles", accountService.lecture(id).getRoles());
        model.addAttribute("roles", accountService.roleList());
        return "utilisateur/ajout";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.warn("Suppression user");
        accountService.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.SEARCH_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("userNew", accountService.lecture(id));
        return "utilisateur/search";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister users");
        model.addAttribute("users", accountService.utilisateurListPage(page).getContent());
        model.addAttribute("totalElements", accountService.utilisateurListPage(page).getTotalElements());
        model.addAttribute("pages", new int[accountService.utilisateurListPage(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("user", accountService.currentUtilisateur());
        return "utilisateur/liste";
    }


    @GetMapping(Endpoint.PASSWORD_ENDPOINT)
    public String changePasswordForm(Model model) {
        model.addAttribute("changePassword", new ChangePassword());
        model.addAttribute("user", accountService.currentUtilisateur());
        return "utilisateur/password";
    }

    @PostMapping(Endpoint.PASSWORD_ENDPOINT)
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
            return "utilisateur/password";
        }
        return "utilisateur/password";    }


}
