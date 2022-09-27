package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Approvission;
import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.repository.ApprovissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovissionService {
    @Autowired
    private ApprovissionRepository approvissionRepository;

    public void ajout(Approvission approvission) {

        double total = 0;
        int qte = approvission.getQuantite();
        List<Produit> produits = approvission.getProduits();
        for (Produit p : produits){
            total+=p.getPrixEngros();
        }
//        approvission.setMontantTotal(total);
        approvission.setMontantRestant(approvission.getMontantRestant()-approvission.getMontantPaye());

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
}
