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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionBindingListener;
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
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("roles", accountService.roleList());
        model.addAttribute("user", accountService.currentUtilisateur());
        return "utilisateur/ajout";
    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("utilisateur") @Valid Utilisateur utilisateur, Errors errors, Model model) {
        LOGGER.info("Ajout user dans la bd");
        auditService.ajoutAudit(new Audit("Ajout/Update Utilisateur", utilisateur.toString()));

        model.addAttribute("user", accountService.currentUtilisateur());
        if (errors.hasErrors()) {

            model.addAttribute("roles", accountService.roleList());
            return "utilisateur/ajout";

        } else {
            Utilisateur utilisateur1 = accountService.addUtilisateur(utilisateur);
            model.addAttribute("utilisateur", utilisateur1);
            return "utilisateur/info";
        }


    }

    @GetMapping(Endpoint.UPDATE_ENDPOINT)
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update user");
        auditService.ajoutAudit(new Audit("Formulaire de modification Utilisateur", accountService.lecture(id).toString()));
        model.addAttribute("user", accountService.currentUtilisateur());
        model.addAttribute("utilisateur", accountService.lecture(id));
        model.addAttribute("userRoles", accountService.lecture(id).getRoles());
        model.addAttribute("roles", accountService.roleList());
        return "utilisateur/ajout";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.warn("Desactivation user");
        auditService.ajoutAudit(new Audit("Suppression Utilisateur", accountService.lecture(id).toString()));

        accountService.desactiver(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.INFO_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", accountService.currentUtilisateur());
        model.addAttribute("utilisateur", accountService.lecture(id));
        return "utilisateur/info";
    }
    @GetMapping(Endpoint.DETAILS_ENDPOINT)
    public String rechercherParNom(@RequestParam(defaultValue = "0") int page, @RequestParam String nom, Model model) {

        Page<Utilisateur> utilisateurPage = accountService.utilisateurNomPage(nom, page);

        model.addAttribute("utilisateurs", utilisateurPage.getContent());
        model.addAttribute("totalElement", utilisateurPage.getTotalElements());
        model.addAttribute("totalPage", new int[utilisateurPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", utilisateurPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", accountService.currentUtilisateur());
        return "utilisateur/liste";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister users");
        auditService.ajoutAudit(new Audit("Liste Utilisateur", "Utilisateurs"));
        Page<Utilisateur> utilisateurPage = accountService.utilisateurListPage(page);

        model.addAttribute("utilisateurs", utilisateurPage.getContent());
        model.addAttribute("totalElement", utilisateurPage.getTotalElements());
        model.addAttribute("totalPage", new int[utilisateurPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", utilisateurPage.getTotalPages());
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
    public String PasswordForm(@ModelAttribute("changePassword") @Valid ChangePassword changePassword,
                              Errors errors, Model model) throws ServletException {
        LOGGER.info("Changement de Password");
        auditService.ajoutAudit(new Audit("Chamgment de Password",
                accountService.currentUtilisateur().getPrenom() + " "
                        + accountService.currentUtilisateur().getNom()));
        model.addAttribute("user", accountService.currentUtilisateur());

        if (accountService.passwordEncodeVerifie(changePassword) == 0) {
            accountService.passwordEncodeVerifie(changePassword);
            return "redirect:/";
        } else if (accountService.passwordEncodeVerifie(changePassword) == 1) {
            errors.rejectValue("newPassword", "",
                    "Le password et la confirmation sont different");
        } else {
            errors.rejectValue("oldPassword", "",
                    "L'ancien password est incorrect");
        }
        return "utilisateur/password";
    }

    @GetMapping(Endpoint.AUDIT_ENDPOINT)
    public String audit(@RequestParam Long id, @RequestParam(defaultValue = "0") int page, Model model) {
        LOGGER.info("Consultation Audit");
        auditService.ajoutAudit(new Audit("Consulation Audit",
                accountService.lecture(id).getPrenom()+" "+
                        accountService.lecture(id).getNom()));

        Page<Audit> auditPage = auditService.auditList(id, page);

        model.addAttribute("audits", auditPage.getContent());
        model.addAttribute("totalElement", auditPage.getTotalElements());
        model.addAttribute("totalPage", new int[auditPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", auditPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("id", id);
        model.addAttribute("user", accountService.currentUtilisateur());
        model.addAttribute("uAudit", accountService.lecture(id));

        return "utilisateur/audit";
    }

}
