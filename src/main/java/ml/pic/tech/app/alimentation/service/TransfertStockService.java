package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Magasin;
import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.domaine.TransfertStock;
import ml.pic.tech.app.alimentation.repository.TransfertStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void ajout(TransfertStock transfert) throws Exception {


        Stock stockSortie = stockService.rechercheParProdAndMag(transfert.getProduit(),
                transfert.getMagasinDepart());
        Stock stockEntree = stockService.rechercheParProdAndMag(transfert.getProduit(),
                transfert.getMagasinDestination());

        Produit produit = stockSortie.getProduit();
        Magasin magasinDepart = stockSortie.getMagasin();
        Magasin magasinDestination = transfert.getMagasinDestination();

        Long idStockSortie = stockSortie.getId();
        Long idStockEntree = stockEntree.getId();
        int qteTransf = transfert.getQuantite();
        int qteStock = stockSortie.getQuantite();

        if (qteStock>=qteTransf) {
            stockService.updateSortie(idStockSortie, qteTransf );
            stockService.updateEntree(idStockEntree, qteTransf );
            TransfertRepository.save(transfert);
        } else {
            throw new Exception("La quantite de transfert est superieure a celle du stock");

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
}
