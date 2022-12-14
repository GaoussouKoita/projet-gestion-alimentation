package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.domaine.RetourProduit;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.*;
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
@RequestMapping(Endpoint.RETOUR_PRODUIT_ENDPOINT)
public class RetourProduitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private RetourProduitService service;
    @Autowired
    private PersonneService personneService;
    @Autowired
    private AccountService userService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private MagasinService magasinService;
    @Autowired
    private VenteService venteService;
    @Autowired
    private AuditService auditService;


    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model) {
        LOGGER.info("Formulaire retourProduit");
        auditService.ajoutAudit(new Audit("Formulaire Retour Produit", "Nouveau Retour Produit"));
        model.addAttribute("retourProduit", new RetourProduit());
        model.addAttribute("personnes", personneService.liste());
        model.addAttribute("userId", userService.currentUtilisateur().getId());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("ventes", venteService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("user", userService.currentUtilisateur());
        return "retourProduit/ajout";

    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("retourProduit") @Valid RetourProduit retourProduit, Errors errors, Model model) {

        LOGGER.info("Ajout de retoutProduit dans la bd");
        auditService.ajoutAudit(new Audit("Ajout/Update Retour Produit", retourProduit.toString()));

        model.addAttribute("user", userService.currentUtilisateur());

        if (errors.hasErrors()) {
            model.addAttribute("personnes", personneService.liste());
            model.addAttribute("userId", userService.currentUtilisateur().getId());
            model.addAttribute("produits", produitService.liste());
            model.addAttribute("ventes", venteService.liste());
            model.addAttribute("magasins", magasinService.liste());
            return "retourProduit/ajout";
        }
        service.ajout(retourProduit);

        return "redirect:liste";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression d'une Retour Produit");
        auditService.ajoutAudit(new Audit("Suppression Retour Produit", service.lecture(id).toString()));

        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.DETAILS_ENDPOINT)
    public String allNomP(Model model, @RequestParam String nom, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister retourProduits");
        auditService.ajoutAudit(new Audit("Liste Retour Produit", "Retour Produits"));

        Page<RetourProduit> retourProduitPage = service.listeParNom(nom,page);
        model.addAttribute("retourProduits", retourProduitPage.getContent());

        model.addAttribute("totalElement", retourProduitPage.getTotalElements());
        model.addAttribute("totalPage", new int[retourProduitPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", retourProduitPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "retourProduit/liste";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister retourProduits");
        auditService.ajoutAudit(new Audit("Liste Retour Produit", "Retour Produits"));

        Page<RetourProduit> retourProduitPage = service.liste(page);
        model.addAttribute("retourProduits", retourProduitPage.getContent());

        model.addAttribute("totalElement", retourProduitPage.getTotalElements());
        model.addAttribute("totalPage", new int[retourProduitPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", retourProduitPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "retourProduit/liste";
    }
}
