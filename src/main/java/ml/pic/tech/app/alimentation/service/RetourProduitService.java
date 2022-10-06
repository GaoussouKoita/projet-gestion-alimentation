package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Magasin;
import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.domaine.RetourProduit;
import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.repository.RetourProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RetourProduitService {
    @Autowired
    private RetourProduitRepository retourProduitRepository;
    @Autowired
    private StockService stockService;

    public void ajout(RetourProduit retourProduit) {

        Produit produit = retourProduit.getProduit();
        Magasin magasin = retourProduit.getMagasin();
        Stock stock = stockService.rechercheParProdAndMag(produit, magasin);
        System.err.println("Stock : " + stock);
        if (stock == null) {
            stock = new Stock();
            stock.setQuantite(retourProduit.getQuantite());
            stock.setMagasin(retourProduit.getMagasin());
            stock.setProduit(retourProduit.getProduit());
            stockService.ajout(stock);
        } else {
            stockService.updateEntree(stock.getId(), retourProduit.getQuantite());
        }

        retourProduitRepository.save(retourProduit);
    }

    public RetourProduit lecture(Long id) {
        return retourProduitRepository.findById(id).get();
    }

    public void suppression(Long id) {
        retourProduitRepository.deleteById(id);
    }

    public List<RetourProduit> liste() {
        return retourProduitRepository.findAll(Sort.by("date").descending());
    }

    public Page<RetourProduit> liste(int p) {
        return retourProduitRepository.findAll(PageRequest.of(p, 9, Sort.by("date").descending()));
    }
}
