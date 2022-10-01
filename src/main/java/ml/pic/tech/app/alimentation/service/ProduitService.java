package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {
    @Autowired
    private ProduitRepository produitRepository;

    public void ajout(Produit produit) {
        produitRepository.save(produit);
    }

    public Page<Produit> rechParNom(String nom) {
        PageImpl<Produit> produits =
                new PageImpl<>(produitRepository.findByNomContaining(nom));
        return produits;
    }

    public Produit lecture(Long id) {
        return produitRepository.findById(id).get();
    }

    public void suppression(Long id) {
        produitRepository.deleteById(id);
    }

    public List<Produit> liste() {
        return produitRepository.findAll();
    }


    public Page<Produit> liste(int page) {
        return produitRepository.findAll(PageRequest.of(page, 8));
    }
}
