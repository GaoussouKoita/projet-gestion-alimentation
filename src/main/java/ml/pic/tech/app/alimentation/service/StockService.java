package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Magasin;
import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public void ajout(Stock stock) {
        stockRepository.save(stock);
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
}
