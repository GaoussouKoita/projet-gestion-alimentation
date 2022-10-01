package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Magasin;
import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.domaine.TransfertStock;
import ml.pic.tech.app.alimentation.repository.TransfertStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransfertStockService {
    @Autowired
    private TransfertStockRepository TransfertRepository;
    @Autowired
    private StockService stockService;

    public void ajout(TransfertStock transfert) {


        Produit produitTransf = transfert.getProduit();
        Magasin magDepart = transfert.getMagasinDepart();
        Magasin magDest = transfert.getMagasinDestination();


        Stock stockSortie = new Stock();
        Stock stockEntree = new Stock();
        stockSortie = stockService.rechercheParProdAndMag(produitTransf, magDepart);
        stockEntree = stockService.rechercheParProdAndMag(produitTransf, magDest);


        Long idStockSortie = stockSortie.getId();
        Long idStockEntree = Long.valueOf(0);
        int qteTransf = transfert.getQuantite();
        int qteStock = stockSortie.getQuantite();
        if (qteStock >= qteTransf) {
            stockService.updateSortie(idStockSortie, qteTransf);

            if (stockEntree == null) {
                stockEntree = new Stock();
                stockEntree.setQuantite(qteTransf);
                stockEntree.setMagasin(magDest);
                stockEntree.setProduit(produitTransf);
                stockService.ajout(stockEntree);
            } else {
                idStockEntree = stockEntree.getId();
                stockService.updateEntree(idStockEntree, qteTransf);
            }

            System.err.println("Stock entree" + stockEntree);

            TransfertRepository.save(transfert);
        }

    }

    public TransfertStock lecture(Long id) {
        return TransfertRepository.findById(id).get();
    }

    public void suppression(Long id) {
        TransfertRepository.deleteById(id);
    }

    public List<TransfertStock> liste() {
        return TransfertRepository.findAll();
    }
    public Page<TransfertStock> liste(int page) {
        return TransfertRepository.findAll(PageRequest.of(page, 9));
    }
}
