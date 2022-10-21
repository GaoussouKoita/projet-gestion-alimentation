package ml.pic.tech.app.alimentation.controller;


import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.domaine.IO_Produits;
import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.domaine.Vente;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.service.StockService;
import ml.pic.tech.app.alimentation.service.VenteService;
import ml.pic.tech.app.alimentation.utils.Constante;
import ml.pic.tech.app.alimentation.utils.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private AccountService userService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private StockService stockService;
    @Autowired
    private VenteService venteService;


    @GetMapping(Endpoint.ACCEUIL_ENDPOINT)
    public String home(Model model) {
        Integer nbreProduitVendu = 0;
        double totalVente = 0;
        List<Vente> venteDuJour = venteService.listeParDate(LocalDate.now());
        List<Stock> stockProdRupture = new ArrayList<>();

        for (Stock stock : stockService.liste()) {
            if (stock.getQuantite() <= Constante.NBRE_STOCK_RUPTURE) {
                stockProdRupture.add(stock);
            }
        }

        for (Vente vente : venteDuJour) {
            for (IO_Produits io_produits : vente.getIo_produits()) {
                nbreProduitVendu += io_produits.getQuantite();
            }
            totalVente += vente.getMontant();
        }

        LOGGER.info("Page d'acceuil");
        auditService.ajoutAudit(new Audit("Acceuil", "Page Principale"));

        model.addAttribute("stocks", stockProdRupture);
        model.addAttribute("nbreProduit", nbreProduitVendu);
        model.addAttribute("totalVente", totalVente);
        model.addAttribute("user", userService.currentUtilisateur());
        return "index";
    }



    @GetMapping(Endpoint.LOGIN_ENDPOINT)
    public String login() {
        LOGGER.info("Page d'Authentification");
        auditService.ajoutAudit(new Audit("Demande Authentification", "Login page"));
        return "login";
    }

}
