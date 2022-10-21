package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.domaine.TransfertStock;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.service.MagasinService;
import ml.pic.tech.app.alimentation.service.ProduitService;
import ml.pic.tech.app.alimentation.service.TransfertStockService;
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
@RequestMapping(Endpoint.TRANSFERT_STOCK_ENDPOINT)
public class TransfertStockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private TransfertStockService service;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private MagasinService magasinService;
    @Autowired
    private AccountService userService;
    @Autowired
    private AuditService auditService;

    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model) {
        LOGGER.info("Formulaire Approvision");
        auditService.ajoutAudit(new Audit("Formulaire Transfert Stock","Nouveau Transfert Stock"));
        model.addAttribute("transfertStock", new TransfertStock());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("userId", userService.currentUtilisateur().getId());
        model.addAttribute("user", userService.currentUtilisateur());
        return "transfertStock/ajout";
    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("transfertStock") @Valid TransfertStock transfertStock, Errors errors, Model model) {

        LOGGER.info("Ajout d'Approvision dans la bd");
        auditService.ajoutAudit(new Audit("Ajout/Update Transfert Stock", transfertStock.toString()));
        model.addAttribute("user", userService.currentUtilisateur());
        if (errors.hasErrors()) {
            model.addAttribute("produits", produitService.liste());
            model.addAttribute("magasins", magasinService.liste());
            model.addAttribute("userId", userService.currentUtilisateur().getId());
            return "transfertStock/ajout";
        }
        service.ajout(transfertStock);
        return "redirect:liste";
    }

    @GetMapping(Endpoint.UPDATE_ENDPOINT)
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update d'Approvision");
        auditService.ajoutAudit(new Audit("Formulaire Update Transfert Stock", service.lecture(id).toString()));
        model.addAttribute("transfertStock", service.lecture(id));
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("userId", userService.currentUtilisateur().getId());
        model.addAttribute("user", userService.currentUtilisateur());
        return "transfertStock/ajout";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression d'Approvision");
        auditService.ajoutAudit(new Audit("Suppression Transfert Stock", service.lecture(id).toString()));

        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.INFO_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("transfertStock", service.lecture(id));
        return "transfertStock/search";
    }

    @GetMapping(Endpoint.DETAILS_ENDPOINT)
    public String rechNom(Model model, @RequestParam String nom, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Approvision");
        auditService.ajoutAudit(new Audit("Liste Transfert Stock par Nom", nom));

        Page<TransfertStock> transfertStockPage = service.listeParNom(nom, page);
        model.addAttribute("transfertStocks", transfertStockPage.getContent());

        model.addAttribute("totalElement", transfertStockPage.getTotalElements());
        model.addAttribute("totalPage", new int[transfertStockPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", transfertStockPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "transfertStock/liste";
    }
    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Approvision");
        auditService.ajoutAudit(new Audit("Liste Transfert Stock", "Transferts Stocks"));
        Page<TransfertStock> transfertStockPage = service.liste(page);
        model.addAttribute("transfertStocks", transfertStockPage.getContent());

        model.addAttribute("totalElement", transfertStockPage.getTotalElements());
        model.addAttribute("totalPage", new int[transfertStockPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", transfertStockPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "transfertStock/liste";
    }
}