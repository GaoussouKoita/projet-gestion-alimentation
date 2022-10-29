package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.IO_Produits;
import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.domaine.Vente;
import ml.pic.tech.app.alimentation.repository.VenteRepository;
import ml.pic.tech.app.alimentation.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VenteService {
    @Autowired
    private VenteRepository venteRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private IO_ProduitsService io_produitsService;

    public Vente ajout(Vente vente) {

        double total = 0;

        List<IO_Produits> io_produits = vente.getIo_produits();

        for (IO_Produits io_p : io_produits) {
            io_p.setType(vente.getTypeVente());

            io_p.setProduit(produitService.prodParCodeBarre1(io_p.getProduit().getCodeBarre1()));

            if (vente.getTypeVente().toUpperCase().equals("ENGROS")) {
                io_p.setPrix(io_p.getProduit().getPrixEngros());
                total += io_p.getQuantite() * io_p.getPrix();
            } else {
                io_p.setPrix(io_p.getProduit().getPrix());
                total += io_p.getQuantite() * io_p.getPrix();
            }
            Stock stock = stockService.rechercheParProdAndMag(io_p.getProduit(), vente.getMagasin());
            if (stock == null) {
                stock = new Stock();
                stock.setQuantite(io_p.getQuantite());
                stock.setMagasin(vente.getMagasin());
                stock.setProduit(io_p.getProduit());
                stockService.ajout(stock);
            } else {

                if (isStockSup(io_p, stock)) {
                    stockService.updateSortie(stock.getId(), io_p.getQuantite());
                }
            }
        }

        vente.setMontant(total - vente.getRemise());
        return venteRepository.save(vente);
    }

    public Vente lecture(Long id) {
        return venteRepository.findById(id).get();
    }

    public void suppression(Long id) {
        venteRepository.deleteById(id);
    }

    public List<Vente> liste() {
        return venteRepository.findAll(Sort.by("date").descending());
    }

    public boolean isStockSup(IO_Produits io_produits, Stock stock) {
        if (io_produits.getQuantite() <= stock.getQuantite()) {
            return true;
        }
        return false;
    }

    public boolean isVerifieVenteStockSup(Vente vente) {
        List<IO_Produits> io_produits = vente.getIo_produits();

        for (IO_Produits io_p : io_produits) {
            io_p.setProduit(produitService.prodParCodeBarre1(io_p.getProduit().getCodeBarre1()));
            Stock stock = stockService.rechercheParProdAndMag(io_p.getProduit(), vente.getMagasin());

            if (stock != null && isStockSup(io_p, stock)) {
                return true;
            }
        }
        return false;
    }

    public Page<Vente> liste(int page) {
        return venteRepository.findAll(PageRequest.of(page, Constante.NBRE_PAR_PAGE,
                Sort.by("date").descending()));
    }

    public Page<Vente> listeParDate(LocalDate date, int page) {
        return venteRepository.findByDate(date, PageRequest.of(page, Constante.NBRE_PAR_PAGE, Sort.by("date").descending().
                and(Sort.by("heure").ascending())));
    }

    public List<Vente> listeParDate(LocalDate date) {
        return venteRepository.findByDate(date);
    }

    public List<Vente> listeDateBetween(LocalDate date1, LocalDate date2) {
        return venteRepository.findByDateBetween(date1, date2);
    }
}
