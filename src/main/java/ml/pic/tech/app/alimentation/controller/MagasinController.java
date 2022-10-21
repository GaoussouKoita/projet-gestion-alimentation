package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.domaine.Magasin;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.service.MagasinService;
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
@RequestMapping(Endpoint.MAGASIN_ENDPOINT)
public class MagasinController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private MagasinService service;
    @Autowired
    private AccountService userService; @Autowired
    private AuditService auditService;

    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model) {
        LOGGER.info("Formulaire Magasin");
        auditService.ajoutAudit(new Audit("Formulaire Magasin", "Nouveau Magasin"));
        model.addAttribute("magasin", new Magasin());
        model.addAttribute("user", userService.currentUtilisateur());
        return "magasin/ajout";
    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("magasin") @Valid Magasin magasin, Errors errors, Model model) {
        LOGGER.info("Ajout de Magasin dans la bd");
        auditService.ajoutAudit(new Audit("Ajout/Update Magasin", magasin.toString()));
        model.addAttribute("user", userService.currentUtilisateur());

        if (errors.hasErrors()) {
            return "magasin/ajout";
        } else {
            service.ajout(magasin);
        }
        return "redirect:liste";
    }

    @GetMapping(Endpoint.UPDATE_ENDPOINT)
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Magasin");
        auditService.ajoutAudit(new Audit("Formulaire Update", service.lecture(id).toString()));
        model.addAttribute("magasin", service.lecture(id));
        model.addAttribute("user", userService.currentUtilisateur());
        return "magasin/ajout";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Magasin");
        auditService.ajoutAudit(new Audit("Suppression Magasin", service.lecture(id).toString()));
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.INFO_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("magasin", service.lecture(id));
        model.addAttribute("user", userService.currentUtilisateur());
        return "magasin/search";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Magasins");
        auditService.ajoutAudit(new Audit("Liste Magasin", "Magasins"));
        Page<Magasin> magasinPage = service.liste(page);
        model.addAttribute("magasins", magasinPage.getContent());

        model.addAttribute("totalElement", magasinPage.getTotalElements());
        model.addAttribute("totalPage", new int[magasinPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", magasinPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "magasin/liste";
    }
}
