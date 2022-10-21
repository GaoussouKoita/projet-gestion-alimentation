package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.domaine.Vente;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.service.MagasinService;
import ml.pic.tech.app.alimentation.service.ProduitService;
import ml.pic.tech.app.alimentation.service.VenteService;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@Controller
@RequestMapping(Endpoint.VENTE_ENDPOINT)
public class VenteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private VenteService service;
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
        LOGGER.info("Formulaire Vente");
        auditService.ajoutAudit(new Audit("Formulaire Vente", "Nouvelle Vente"));
        model.addAttribute("vente", new Vente());
        model.addAttribute("userId", userService.currentUtilisateur().getId());
        model.addAttribute("produits", produitService.liste());
//        model.addAttribute("io_prods", new ArrayList<IO_Produits>());
        model.addAttribute("user", userService.currentUtilisateur());
        model.addAttribute("magasins", magasinService.liste());
        return "vente/ajout";
    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("vente") @Valid Vente vente,
                      Errors errors, Model model) {
        LOGGER.info("Ajout de Vente dans la bd");
        model.addAttribute("user", userService.currentUtilisateur());

        auditService.ajoutAudit(new Audit("Ajout/Update Vente", vente.toString()));

        if (errors.hasErrors()) {
            model.addAttribute("userId", userService.currentUtilisateur().getId());
            model.addAttribute("produits", produitService.liste());
            model.addAttribute("produits", produitService.liste());
//            model.addAttribute("io_prods", new ArrayList<IO_Produits>());
            model.addAttribute("magasins", magasinService.liste());
            return "vente/ajout";
        }
//        vente.setIo_produits(io_produits);
            service.ajout(vente);
            model.addAttribute("vente", service.lecture(vente.getId()));

        return "vente/info";
    }

    @GetMapping(Endpoint.UPDATE_ENDPOINT)
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Vente");
        auditService.ajoutAudit(new Audit("Formulaire Update Vente", service.lecture(id).toString()));
        model.addAttribute("vente", service.lecture(id));
        model.addAttribute("userId", userService.currentUtilisateur().getId());
        model.addAttribute("produits", produitService.liste());
        model.addAttribute("magasins", magasinService.liste());
        model.addAttribute("user", userService.currentUtilisateur());
        return "vente/ajout";
    }

    @GetMapping(Endpoint.DELETE_ENDPOINT)
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Vente");
        auditService.ajoutAudit(new Audit("Suppression Vente", service.lecture(id).toString()));
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.INFO_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("vente", service.lecture(id));
        model.addAttribute("user", userService.currentUtilisateur());
        return "vente/info";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Ventes");
        auditService.ajoutAudit(new Audit("Liste Vente", "Ventes"));
        Page<Vente> ventePage = service.liste(page);
        model.addAttribute("ventes", ventePage.getContent());

        model.addAttribute("totalElement", ventePage.getTotalElements());
        model.addAttribute("totalPage", new int[ventePage.getTotalPages()]);
        model.addAttribute("nbTotalPage", ventePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "vente/liste";
    }

    @GetMapping(Endpoint.DETAILS_ENDPOINT)
    public String allDate(Model model, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Ventes");
        auditService.ajoutAudit(new Audit("Liste Vente", "Ventes"));

        Page<Vente> ventePage = service.listeParDate(date, page);
        model.addAttribute("ventes", ventePage.getContent());

        model.addAttribute("totalElement", ventePage.getTotalElements());
        model.addAttribute("totalPage", new int[ventePage.getTotalPages()]);
        model.addAttribute("nbTotalPage", ventePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "vente/liste";
    }
}