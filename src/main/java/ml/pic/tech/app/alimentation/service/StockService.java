package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Magasin;
import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.repository.StockRepository;
import ml.pic.tech.app.alimentation.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public Stock ajout(Stock stock) {

        Stock stockEncours = rechercheParProdAndMag(stock.getProduit(), stock.getMagasin());

        if (stockEncours != null) {
            stockEncours.setQuantite(stockEncours
                    .getQuantite() + stock.getQuantite());
            return stockRepository.save(stockEncours);
        }
        return stockRepository.save(stock);

    }

    public Stock lecture(Long id) {
        return stockRepository.findById(id).get();
    }

    public void suppression(Long id) {
        stockRepository.deleteById(id);
    }

    public void updateSortie(Long id, int qte) {
        stockRepository.updateByIdAndQuantiteSortie(id, qte);
    }

    public void updateEntree(Long id, int qte) {
        stockRepository.updateByIdAndQuantiteEntree(id, qte);
    }

    public Stock rechercheParProdAndMag(Produit prod, Magasin mag) {
        return stockRepository.findByProduitAndMagasin(prod, mag);
    }

    public Stock rechercheParProd(Produit produit){
        return stockRepository.findByProduit(produit);
    }

    public List<Stock> liste() {
        return stockRepository.findAll();
    }

    public Page<Stock> liste(int page) {
        return stockRepository.findAll(PageRequest.of(page, Constante.NBRE_PAR_PAGE));
    }
    public Page<Stock> listeProdNom(String nom,int page) {
        return stockRepository.findByProduitNomContaining(nom, PageRequest.of(page, Constante.NBRE_PAR_PAGE));
    }
}
