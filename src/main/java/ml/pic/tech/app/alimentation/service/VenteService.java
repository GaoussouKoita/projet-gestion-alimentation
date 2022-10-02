package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.IO_Produits;
import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.domaine.Vente;
import ml.pic.tech.app.alimentation.repository.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenteService {
    @Autowired
    private VenteRepository VenteRepository;
    @Autowired
    private StockService stockService;

    public void ajout(Vente vente) {

        double total =0;

        List<IO_Produits> io_produits = vente.getIo_produits();

        for (IO_Produits io_p : io_produits) {

            if (vente.getTypeVente().toUpperCase().equals("ENGROS")) {
                io_p.setPrix(io_p.getProduit().getPrixEngros());
                total += io_p.getQuantite()*io_p.getPrix();
            } else {
                io_p.setPrix(io_p.getProduit().getPrix());
                total += io_p.getQuantite()*io_p.getPrix();
            }
            Stock stock = stockService.rechercheParProdAndMag(io_p.getProduit(), vente.getMagasin());
            if (stock == null) {
                stock = new Stock();
                stock.setQuantite(io_p.getQuantite());
                stock.setMagasin(vente.getMagasin());
                stock.setProduit(io_p.getProduit());
                stockService.ajout(stock);
            } else {
                stockService.updateEntree(stock.getId(), io_p.getQuantite());
            }
        }
        vente.setMontant(total- vente.getRemise());
        System.err.println("Ser "+vente);
        VenteRepository.save(vente);
    }

    public Vente lecture(Long id) {
        return VenteRepository.findById(id).get();
    }

    public void suppression(Long id) {
        VenteRepository.deleteById(id);
    }

    public List<Vente> liste() {
        return VenteRepository.findAll();
    }
    public Page<Vente> liste(int page) {
        return VenteRepository.findAll(PageRequest.of(page, 9));
    }
}
