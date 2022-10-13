package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Approvission;
import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.*;
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
@RequestMapping(Endpoint.APPROVISSION_ENDPOINT)
public class ApprovissionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private ApprovissionService service;
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
        LOGGER.info("Formulaire Approvission");
        auditService.ajoutAudit(new Audit("Formulaire", "Nouvelle Approvission"));

        model.addAttribute("approvission", new Approvission());
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("userId", userService.currentUtilisateur().getId());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("user", userService.currentUtilisateur());

        return "approvission/ajout";

    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("approvission") @Valid Approvission approvission, Errors errors, Model model) {

        LOGGER.info("Ajout d'Approvission dans la bd");
        model.addAttribute("user", userService.currentUtilisateur());
        auditService.ajoutAudit(new Audit("Ajout/Update Approvision", approvission.toString()));


        if (errors.hasErrors()) {
            model.addAttribute("personnes", personneService.liste());
            model.addAttribute("userId", userService.currentUtilisateur().getId());
            model.addAttribute("produits", produitService.liste());
            model.addAttribute("magasins", magasinService.liste());

            return "approvission/ajout";
        } else {
            service.ajout(approvission);
        }
        model.addAttribute("approvission", service.lecture(approvission.getId()));
        return  "approvission/search";
    }

    @GetMapping(Endpoint.UPDATE_ENDPOINT)
    public String modifier(@RequestParam("id") Long id, Model model) {
        /*LOGGER.info("Mise a jour d'un Approvision");
        auditService.ajoutAudit(new Audit("Formulaire Update", ));

        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("userId", userService.currentUtilisateur().getId());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("user", userService.currentUtilisateur());
*/
        return "approvission/ajout";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression d'une Approvission");
        auditService.ajoutAudit(new Audit("Suppression Approvision", service.lecture(id).toString()));

        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.SEARCH_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("approvission", service.lecture(id));
        return "approvission/search";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Approvissions");
        auditService.ajoutAudit(new Audit("Liste"," Approvision"));

        model.addAttribute("approvissions", service.liste(page).getContent());
        model.addAttribute("totalElements", service.liste(page).getTotalElements());
        model.addAttribute("pages", new int[ service.liste(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());

        return "approvission/liste";
    }


}
