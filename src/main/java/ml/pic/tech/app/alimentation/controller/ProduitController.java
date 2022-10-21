package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.service.CategorieService;
import ml.pic.tech.app.alimentation.service.ProduitService;
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
@RequestMapping(Endpoint.PRODUIT_ENDPOINT)
public class ProduitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private ProduitService service;
    @Autowired
    private CategorieService categorieService;
    @Autowired
    private AccountService userService;
    @Autowired
    private AuditService auditService;

    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model) {
        LOGGER.info("Formulaire Produit");
        auditService.ajoutAudit(new Audit("Formulaire Produit", "Nouveau Produit"));
        model.addAttribute("produit", new Produit());
        model.addAttribute("categories", categorieService.liste());
        model.addAttribute("user", userService.currentUtilisateur());
        return "produit/ajout";
    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("produit") @Valid Produit produit, Errors errors, Model model) {
        LOGGER.info("Ajout de Produit dans la bd");
        auditService.ajoutAudit(new Audit("Ajout/Update", produit.toString()));
        model.addAttribute("categories", categorieService.liste());
        model.addAttribute("user", userService.currentUtilisateur());
        if (errors.hasErrors()) {
            model.addAttribute("categories", categorieService.liste());
            return "produit/ajout";
        } else {
            service.ajout(produit);
        }

        return "redirect:liste";
    }

    @GetMapping(Endpoint.UPDATE_ENDPOINT)
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Produit");
        auditService.ajoutAudit(new Audit("Formulaire Update Produit", service.lecture(id).toString()));
        model.addAttribute("user", userService.currentUtilisateur());
        model.addAttribute("produit", service.lecture(id));
        model.addAttribute("categories", categorieService.liste());
        return "produit/ajout";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Produit");
        auditService.ajoutAudit(new Audit("Suppression Produit", service.lecture(id).toString()));
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.DETAILS_ENDPOINT)
    public String rechercher(@RequestParam("nom") String nom, @RequestParam(defaultValue = "0") int page, Model model) {
        LOGGER.info("Recherche de produit par nom");
        auditService.ajoutAudit(new Audit("Recherche Produit par nom", nom));
        Page<Produit> produitPage = service.rechParNom(nom);
        model.addAttribute("produits", produitPage.getContent());

        model.addAttribute("totalElement", produitPage.getTotalElements());
        model.addAttribute("totalPage", new int[produitPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", produitPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "produit/liste";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Produits");
        auditService.ajoutAudit(new Audit("Liste Produit", "Produits"));

        Page<Produit> produitPage = service.liste(page);
        model.addAttribute("produits", produitPage.getContent());

        model.addAttribute("totalElement", produitPage.getTotalElements());
        model.addAttribute("totalPage", new int[produitPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", produitPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "produit/liste";
    }
}
