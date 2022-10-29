package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Approvision;
import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.*;
import ml.pic.tech.app.alimentation.utils.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping(Endpoint.APPROVISION_ENDPOINT)
public class ApprovisionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private ApprovisionService service;
    @Autowired
    private PersonneService personneService;
    @Autowired
    private AccountService userService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private MagasinService magasinService;
    @Autowired
    private AuditService auditService;


    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model) {
        LOGGER.info("Formulaire Approvision");
        auditService.ajoutAudit(new Audit("Formulaire", "Nouvelle Approvision"));

        model.addAttribute("approvision", new Approvision());
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("userId", userService.currentUtilisateur().getId());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("user", userService.currentUtilisateur());

        return "approvision/ajout";

    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("approvision") @Valid Approvision approvision, Errors errors, Model model) {

        LOGGER.info("Ajout d'Approvision dans la bd");
        model.addAttribute("user", userService.currentUtilisateur());
        auditService.ajoutAudit(new Audit("Ajout/Update Approvision", approvision.toString()));

        if (errors.hasErrors()) {
            model.addAttribute("personnes", personneService.liste());
            model.addAttribute("userId", userService.currentUtilisateur().getId());
            model.addAttribute("produits", produitService.liste());
            model.addAttribute("magasins", magasinService.liste());

            return "approvision/ajout";
        }
        approvision = service.ajout(approvision);

        model.addAttribute("approvision", service.lecture(approvision.getId()));

        return "approvision/info";
    }


    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression d'une Approvision");
        auditService.ajoutAudit(new Audit("Suppression Approvision", service.lecture(id).toString()));

        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.INFO_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.currentUtilisateur());
        model.addAttribute("approvision", service.lecture(id));
        return "approvision/info";
    }

    @GetMapping(Endpoint.DETAILS_ENDPOINT)
    public String rechercherDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam(defaultValue = "0") int page, Model model) {
        LOGGER.info("Lister Approvisions");
        Page<Approvision> approvisionPage = service.listeDate(date, page);
        model.addAttribute("approvisions", approvisionPage.getContent());

        model.addAttribute("totalElement", approvisionPage.getTotalElements());
        model.addAttribute("totalPage", new int[approvisionPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", approvisionPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());

        return "approvision/liste";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Approvisions");

        Page<Approvision> approvisionPage = service.liste(page);
        model.addAttribute("approvisions", approvisionPage.getContent());

        model.addAttribute("totalElement", approvisionPage.getTotalElements());
        model.addAttribute("totalPage", new int[approvisionPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", approvisionPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());

        return "approvision/liste";
    }


}
