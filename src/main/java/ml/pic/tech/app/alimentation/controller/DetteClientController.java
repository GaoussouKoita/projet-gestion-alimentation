package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.domaine.DetteClient;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.service.DetteClientService;
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
@RequestMapping(Endpoint.DETTE_CLIENT_ENDPOINT)
public class DetteClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private DetteClientService service;
    @Autowired
    private PersonneService personneService;
    @Autowired
    private AccountService userService;
    @Autowired
    private AuditService auditService;


    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model) {
        LOGGER.info("Formulaire Dette");
        auditService.ajoutAudit(new Audit("Formulaire Dette Client", "Nouvelle Dette Client"));
        model.addAttribute("detteClient", new DetteClient());
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("user", userService.currentUtilisateur());
        return "detteClient/ajout";
    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("detteClient") @Valid DetteClient detteClient, Errors errors, Model model) {
        LOGGER.info("Ajout de Dette dans la bd");
        auditService.ajoutAudit(new Audit("Ajout/Update Dette Client", detteClient.toString()));
        model.addAttribute("user", userService.currentUtilisateur());
        if (errors.hasErrors()) {
            model.addAttribute("personnes", personneService.liste());
            return "detteClient/ajout";
        } else {
        service.ajout(detteClient);}
        return "redirect:liste";
    }

    @GetMapping(Endpoint.UPDATE_ENDPOINT)
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Dette");
        auditService.ajoutAudit(new Audit("Formulaire Update Dette Client", service.lecture(id).toString()));
        model.addAttribute("detteClient", service.lecture(id));
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("user", userService.currentUtilisateur());
        return "detteClient/ajout";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Dette");
        auditService.ajoutAudit(new Audit("Suppression Dette Client", service.lecture(id).toString()));
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.INFO_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("detteClient", service.lecture(id));
        model.addAttribute("user", userService.currentUtilisateur());
        return "detteClient/search";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Dettes");
        auditService.ajoutAudit(new Audit("Liste Dette Client", "Dettes Clienst"));

        Page<DetteClient> detteClientPage = service.liste(page);
        model.addAttribute("detteClients", detteClientPage.getContent());

        model.addAttribute("totalElement", detteClientPage.getTotalElements());
        model.addAttribute("totalPage", new int[detteClientPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", detteClientPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "detteClient/liste";
    }
}
