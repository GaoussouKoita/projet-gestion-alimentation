package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Approvission;
import ml.pic.tech.app.alimentation.domaine.IO_Produits;
import ml.pic.tech.app.alimentation.domaine.Stock;
import ml.pic.tech.app.alimentation.repository.ApprovissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApprovissionService {

    @Autowired
    private ApprovissionRepository approvissionRepository;
    @Autowired
    private StockService stockService;


    public void ajout(Approvission approvission) {

        double total = approvission.getMontantTotal();

        List<IO_Produits> produits = approvission.getIo_produits();
        for (IO_Produits p : produits) {

            total += p.getQuantite() * p.getPrix();
            Stock stock = stockService.rechercheParProdAndMag(p.getProduit(), approvission.getMagasin());
            if (stock == null) {
                stock = new Stock();
                stock.setQuantite(p.getQuantite());
                stock.setMagasin(approvission.getMagasin());
                stock.setProduit(p.getProduit());
                stockService.ajout(stock);

            } else {

                stockService.updateEntree(stock.getId(), p.getQuantite());
            }

        }

        approvission.setMontantTotal(total);
        approvission.setMontantRestant(total - approvission.getMontantPaye());

        System.err.println(approvission);
        approvissionRepository.save(approvission);

    }

    public Approvission lecture(Long id) {
        return approvissionRepository.findById(id).get();
    }

    public void suppression(Long id) {
        approvissionRepository.deleteById(id);
    }

    public List<Approvission> liste() {
        return approvissionRepository.findAll();
    }

    public Page<Approvission> liste(int page) {
        return approvissionRepository.findAll(PageRequest.of(page, 9));
    }
}
