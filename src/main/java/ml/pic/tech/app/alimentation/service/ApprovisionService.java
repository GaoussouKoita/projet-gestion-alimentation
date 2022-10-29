package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Approvision;
import ml.pic.tech.app.alimentation.domaine.IO_Produits;
import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.repository.ApprovisionRepository;
import ml.pic.tech.app.alimentation.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ApprovisionService {

    @Autowired
    private ApprovisionRepository approvisionRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private IO_ProduitsService io_produitsService;
    @Autowired
    private ProduitService produitService;


    public Approvision ajout(Approvision approvision) {

        double total = 0;
        List<IO_Produits> io_produits = approvision.getIo_produits();
        for (IO_Produits io_p : io_produits) {
            total += io_p.getQuantite() * io_p.getPrix();

            io_p.setProduit(produitService.prodParCodeBarre1(io_p.getProduit().getCodeBarre1()));
            System.err.println(io_p.getProduit());
            Stock stock = stockService.rechercheParProdAndMag(io_p.getProduit(), approvision.getMagasin());
            if (stock == null) {
                stock = new Stock();
                stock.setQuantite(io_p.getQuantite());
                stock.setMagasin(approvision.getMagasin());
                stock.setProduit(io_p.getProduit());
                stockService.ajout(stock);
            } else {

                stockService.updateEntree(stock.getId(), io_p.getQuantite());
            }
        }

        io_produits = io_produitsService.ajoutListe(io_produits);

        approvision.setMontantTotal(total);
        approvision.setMontantRestant(total - approvision.getMontantPaye());
        approvision.setIo_produits(io_produits);

        return approvisionRepository.save(approvision);

    }

    public Approvision lecture(Long id) {
        return approvisionRepository.findById(id).get();
    }

    public void suppression(Long id) {
        approvisionRepository.deleteById(id);
    }

    public List<Approvision> liste() {
        return approvisionRepository.findAll(Sort.by("date").descending());
    }

    public Page<Approvision> liste(int page) {
        return approvisionRepository.findAll(PageRequest.of(page, Constante.NBRE_PAR_PAGE, Sort.by("date").descending()));
    }
    public Page<Approvision> listeDate(LocalDate date, int page) {
        return approvisionRepository.findByDate(date,PageRequest.of(page, Constante.NBRE_PAR_PAGE, Sort.by("date").descending().
                and(Sort.by("heure").ascending())));    }
}
