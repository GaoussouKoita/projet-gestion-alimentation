package ml.pic.tech.app.alimentation.securite.controller;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.securite.entity.ChangePassword;
import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
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
    @Autowired
    private AuditService auditService;


    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model) {
        LOGGER.info("Formulaire user");
        auditService.ajoutAudit(new Audit("Demande Formulaire", "Nouvel Utilisateur"));
        model.addAttribute("userNew", new Utilisateur());
        model.addAttribute("roles", accountService.roleList());
        model.addAttribute("user", accountService.currentUtilisateur());
        return "utilisateur/ajout";
    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("userNew") @Valid Utilisateur utilisateur, Errors errors, Model model) {
        LOGGER.info("Ajout user dans la bd");
        auditService.ajoutAudit(new Audit("Ajout/Update Utilisateur", utilisateur.toString()));

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
        auditService.ajoutAudit(new Audit("Formulaire de modification Utilisateur", accountService.lecture(id).toString()));

        model.addAttribute("user", accountService.currentUtilisateur());
        model.addAttribute("userNew", accountService.lecture(id));
        model.addAttribute("userRoles", accountService.lecture(id).getRoles());
        model.addAttribute("roles", accountService.roleList());
        return "utilisateur/ajout";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.warn("Suppression user");
        auditService.ajoutAudit(new Audit("Suppression Utilisateur", accountService.lecture(id).toString()));

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
        auditService.ajoutAudit(new Audit("Liste Utilisateur", "Utilisateurs"));

        model.addAttribute("users", accountService.utilisateurListPage(page).getContent());
        model.addAttribute("totalElements", accountService.utilisateurListPage(page).getTotalElements());
        model.addAttribute("pages", new int[accountService.utilisateurListPage(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("user", accountService.currentUtilisateur());
        return "utilisateur/liste";
    }


    @GetMapping(Endpoint.PASSWORD_ENDPOINT)
    public String changePasswordForm(Model model) {
        LOGGER.info("Formulaire Changement password");
        auditService.ajoutAudit(new Audit("Formulaire Changement Password",
                accountService.currentUtilisateur().getPrenom() + " "
                        + accountService.currentUtilisateur().getNom()));

        model.addAttribute("changePassword", new ChangePassword());
        model.addAttribute("user", accountService.currentUtilisateur());
        return "utilisateur/password";
    }

    @PostMapping(Endpoint.PASSWORD_ENDPOINT)
    public String PasswordForm(@ModelAttribute("changePassword") @Valid ChangePassword changePassword, Errors errors, Model model) {
        LOGGER.info("Changement de Password");
        auditService.ajoutAudit(new Audit("Chamgment de Password",
        accountService.currentUtilisateur().getPrenom() + " "
                + accountService.currentUtilisateur().getNom()));
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
        return "utilisateur/password";
    }

    @GetMapping(Endpoint.AUDIT_ENDPOINT)
    public String audit(@RequestParam Long id, @RequestParam(defaultValue = "0") int page, Model model) {
        LOGGER.info("Consultation Audit");
        auditService.ajoutAudit(new Audit("Consulation Audit",
                accountService.lecture(id).getPrenom()+" "+
                        accountService.lecture(id).getNom()));

        model.addAttribute("audits", auditService.auditList(id, page));
        model.addAttribute("totalElements", auditService.auditList(id, page).getTotalElements());
        model.addAttribute("pages", new int[auditService.auditList(id, page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("id", id);

        model.addAttribute("user", accountService.currentUtilisateur());

        return "utilisateur/audit";
    }

}
