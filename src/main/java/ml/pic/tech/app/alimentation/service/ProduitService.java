package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.repository.ProduitRepository;
import ml.pic.tech.app.alimentation.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {
    @Autowired
    private ProduitRepository produitRepository;

    public void ajout(Produit produit) {
        produitRepository.save(produit);
    }

    public Page<Produit> rechParNom(String nom, int page) {
       return produitRepository.findByNomContaining(nom, PageRequest.of(page, Constante.NBRE_PAR_PAGE,
               Sort.by("nom").ascending().and(Sort.by("categorie.nom").ascending())));
    }

    public Produit lecture(Long id) {
        return produitRepository.findById(id).get();
    }

    public Produit lecture(String nom) {
        return produitRepository.findByNom(nom);
    }

    public Produit prodParCodeBarre1(Long codeBarre1) {
        return produitRepository.findByCodeBarre1OrCodeBarre2(codeBarre1, codeBarre1);
    }

    public void suppression(Long id) {
        produitRepository.deleteById(id);
    }

    public List<Produit> liste() {
        return produitRepository.findAll(Sort.by("nom").ascending());
    }


    public Page<Produit> liste(int page) {
        return produitRepository.findAll(PageRequest.of(page, Constante.NBRE_PAR_PAGE,
                Sort.by("nom").ascending().and(Sort.by("categorie.nom").ascending())));
    }


}
