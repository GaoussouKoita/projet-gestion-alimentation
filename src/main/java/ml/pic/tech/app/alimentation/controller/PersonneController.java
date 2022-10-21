package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.domaine.Personne;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.service.PersonneService;
import ml.pic.tech.app.alimentation.utils.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(Endpoint.CLIENT_FOURNISSEUR_ENDPOINT)
public class PersonneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private PersonneService service;
    @Autowired
    private AccountService userService;
    @Autowired
    private AuditService auditService;

    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model) {
        LOGGER.info("Formulaire Client-Fournisseur");
        auditService.ajoutAudit(new Audit("Formulaire Client Fournisseur", "Nouveau Client Fournisseur"));
        model.addAttribute("personne", new Personne());
        model.addAttribute("user", userService.currentUtilisateur());
        return "personne/ajout";
    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("personne") @Valid Personne personne, Errors errors, Model model) {
        LOGGER.info("Ajout de Client-Fournisseur dans la bd");
        auditService.ajoutAudit(new Audit("Ajout/Update Client Fournisseur",personne.toString() ));
        model.addAttribute("user", userService.currentUtilisateur());
        if (errors.hasErrors()) {
            return "personne/ajout";

        } else {
        service.ajout(personne);
        }
        return "redirect:liste";
    }

    @GetMapping(Endpoint.UPDATE_ENDPOINT)
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Client-Fournisseur");
        auditService.ajoutAudit(new Audit("Formulaire Update Client Fournisseur", service.lecture(id).toString()));
        model.addAttribute("personne", service.lecture(id));
        model.addAttribute("user", userService.currentUtilisateur());
        return "personne/ajout";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Client-Fournisseur");
        auditService.ajoutAudit(new Audit("Suppression Client Fournisseur", service.lecture(id).toString()));

        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.INFO_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("personne", service.lecture(id));
        return "personne/search";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Client-Fournisseurs");
        auditService.ajoutAudit(new Audit("Liste Client Fournisseur", "Clients Fournisseur"));

        Page<Personne> personnePage = service.liste(page);
        model.addAttribute("personnes", personnePage.getContent());

        model.addAttribute("totalElement", personnePage.getTotalElements());
        model.addAttribute("totalPage", new int[personnePage.getTotalPages()]);
        model.addAttribute("nbTotalPage", personnePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "personne/liste";
    }
}
