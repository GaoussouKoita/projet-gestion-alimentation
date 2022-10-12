package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.IO_Produits;
import ml.pic.tech.app.alimentation.domaine.Vente;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.MagasinService;
import ml.pic.tech.app.alimentation.service.ProduitService;
import ml.pic.tech.app.alimentation.service.VenteService;
import ml.pic.tech.app.alimentation.utils.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

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

    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model) {
        LOGGER.info("Formulaire Vente");
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
                      @ModelAttribute("io_prods") ArrayList<IO_Produits>io_produits,
                      Errors errors, Model model) {
        LOGGER.info("Ajout de Vente dans la bd");
        if (errors.hasErrors()) {
            model.addAttribute("userId", userService.currentUtilisateur().getId());
            model.addAttribute("produits", produitService.liste());
            model.addAttribute("userId", userService.currentUtilisateur().getId());
            model.addAttribute("produits", produitService.liste());
//            model.addAttribute("io_prods", new ArrayList<IO_Produits>());
            model.addAttribute("magasins", magasinService.liste());
            model.addAttribute("user", userService.currentUtilisateur());
            return "vente/ajout";
        }
//        vente.setIo_produits(io_produits);
        System.err.println(vente);
            service.ajout(vente);
            model.addAttribute("vente", service.lecture(vente.getId()));

        return "vente/search";
    }

    @GetMapping(Endpoint.UPDATE_ENDPOINT)
    public String modifier(@RequestParam("id") Long id, Model model) {
        LOGGER.info("Update de Vente");
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
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.SEARCH_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("vente", service.lecture(id));
        model.addAttribute("user", userService.currentUtilisateur());
        return "vente/search";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        LOGGER.info("Lister Ventes");
        model.addAttribute("ventes", service.liste(page).getContent());
        model.addAttribute("totalElements", service.liste(page).getTotalElements());
        model.addAttribute("pages", new int[ service.liste(page).getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "vente/liste";
    }
}