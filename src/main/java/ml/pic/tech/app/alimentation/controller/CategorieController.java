package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.domaine.Categorie;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.service.CategorieService;
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
@RequestMapping(Endpoint.CATEGORIE_ENDPOINT)
public class CategorieController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private CategorieService service;
    @Autowired
    private AccountService userService;
    @Autowired
    private AuditService auditService;

    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model) {
        LOGGER.info("Formulaire Categorie");
        model.addAttribute("categorie", new Categorie());
        model.addAttribute("user", userService.currentUtilisateur());
        auditService.ajoutAudit(new Audit("Formulaire", "Nouvelle Categorie"));
        return "categorie/ajout";
    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("categorie") @Valid  Categorie categorie, Errors errors, Model model) {
        LOGGER.info("Ajout de Categorie dans la bd");
        model.addAttribute("user", userService.currentUtilisateur());
        auditService.ajoutAudit(new Audit("Ajout/Upade de Categorie", categorie.toString()));

        if (errors.hasErrors()) {

            return "categorie/ajout";
        } else {
            service.ajout(categorie);}
        return "redirect:liste";
    }

    @GetMapping(Endpoint.UPDATE_ENDPOINT)
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Mise a jour de Categorie");
        Categorie categorie=service.lecture(id);
        model.addAttribute("categorie",categorie );
        model.addAttribute("user", userService.currentUtilisateur());
        auditService.ajoutAudit(new Audit("Modification de Categorie", categorie.toString()));

        return "categorie/ajout";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Categorie");
        auditService.ajoutAudit(new Audit("Suppression de Categorie", service.lecture(id).toString()));
        service.suppression(id);
        return "redirect:liste";
    }

    @GetMapping(Endpoint.DETAILS_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("categorie", service.lecture(id));
        return "categorie/search";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0")int page) {
        LOGGER.info("Lister Categories");

        Page<Categorie> categoriePage = service.liste(page);
        model.addAttribute("categories", categoriePage.getContent());

        model.addAttribute("totalElement", categoriePage.getTotalElements());
        model.addAttribute("totalPage", new int[categoriePage.getTotalPages()]);
        model.addAttribute("nbTotalPage", categoriePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        auditService.ajoutAudit(new Audit("Liste de Categorie", "Liste de Categorie"));

        return "categorie/liste";
    }
}