package ml.pic.tech.app.alimentation.controller;


import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.domaine.IO_Produits;
import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.domaine.Vente;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.service.StockService;
import ml.pic.tech.app.alimentation.service.VenteService;
import ml.pic.tech.app.alimentation.utils.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
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

        List<Vente> ventesDuJour = venteService.listeParDate(LocalDate.now());
        List<IO_Produits> prodsVente = venteService.produitsDeLaVente(ventesDuJour);
        List<Stock> stockProdRupture = stockService.ruptureStock();

        int nbreProduitVendu = Integer.parseInt(String.valueOf(
                venteService.ventesDuJour(ventesDuJour).get("nbreProduitVendu")));
        double totalVente = Double.parseDouble(
                String.valueOf(venteService.ventesDuJour(ventesDuJour)
                        .get("totalVente")));


        LOGGER.info("Page d'acceuil");
        auditService.ajoutAudit(new Audit("Acceuil", "Page Principale"));

        model.addAttribute("stocks", stockProdRupture);
        model.addAttribute("nbreProduit", nbreProduitVendu);
        model.addAttribute("totalVente", totalVente);
        model.addAttribute("io_produits", prodsVente);
        model.addAttribute("user", userService.currentUtilisateur());
        return "index";
    }


    @GetMapping(Endpoint.DETAILS_ENDPOINT)
    public String venteBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       LocalDate date1,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       LocalDate date2, Model model) {

        List<Vente> ventesBetween = venteService.listeDateBetween(date1, date2);
        List<IO_Produits> prodsVente = venteService.produitsDeLaVente(ventesBetween);

        int nbreProduitVenduBetween = Integer.parseInt(
                String.valueOf(venteService.ventesDuJour(ventesBetween)
                        .get("nbreProduitVendu")));

        double totalVenteBetween = venteService.
                ventesDuJour(ventesBetween).get("totalVente");

        model.addAttribute("user", userService.currentUtilisateur());
        model.addAttribute("nbreProduitBetween", nbreProduitVenduBetween);
        model.addAttribute("totalVenteBetween", totalVenteBetween);
        model.addAttribute("io_prodVenteBetween", prodsVente);
        model.addAttribute("dateDebut", date1);
        model.addAttribute("dateFin", date2);

        home(model);

        return "index";
    }


}
