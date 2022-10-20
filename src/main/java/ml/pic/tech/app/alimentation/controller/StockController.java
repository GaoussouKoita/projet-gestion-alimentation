package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.service.MagasinService;
import ml.pic.tech.app.alimentation.service.ProduitService;
import ml.pic.tech.app.alimentation.service.StockService;
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
@RequestMapping(Endpoint.STOCK_ENDPOINT)
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private StockService service;
    @Autowired
    private MagasinService magasinService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private AccountService userService;
    @Autowired
    private AuditService auditService;

    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model) {
        LOGGER.info("Formulaire Stock");
        auditService.ajoutAudit(new Audit("Formulaire Stock", "Nouveau Stock"));
        model.addAttribute("stock", new Stock());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("user", userService.currentUtilisateur());
        return "stock/ajout";
    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("stock") @Valid Stock stock, Errors errors, Model model) {
        LOGGER.info("Ajout de Stock dans la bd");
        auditService.ajoutAudit(new Audit("Ajout/Update Stock", stock.toString()));
        model.addAttribute("user", userService.currentUtilisateur());
        if (errors.hasErrors()) {
            model.addAttribute("produits", produitService.liste());
            model.addAttribute("magasins", magasinService.liste());
            return "stock/ajout";
        }
        service.ajout(stock);
        return "redirect:liste";
    }

    @GetMapping(Endpoint.UPDATE_ENDPOINT)
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Stock");
        auditService.ajoutAudit(new Audit("Formulaire Update Stock", service.lecture(id).toString()));
        model.addAttribute("stock", service.lecture(id));
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("user", userService.currentUtilisateur());
        return "stock/ajout";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Stock");
        auditService.ajoutAudit(new Audit("Suppression Stock", service.lecture(id).toString()));
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.DETAILS_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("stock", service.lecture(id));
        return "stock/search";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Stock");
        auditService.ajoutAudit(new Audit("Liste Stock", "Stocks"));
        Page<Stock> stockPage = service.liste(page);
        model.addAttribute("stocks", stockPage.getContent());

        model.addAttribute("totalElement", stockPage.getTotalElements());
        model.addAttribute("totalPage", new int[stockPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", stockPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "stock/liste";
    }
}