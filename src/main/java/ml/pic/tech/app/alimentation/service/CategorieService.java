package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Categorie;
import ml.pic.tech.app.alimentation.repository.CategorieRepository;
import ml.pic.tech.app.alimentation.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    public void ajout(Categorie categorie) {
        categorieRepository.save(categorie);
    }

    public Categorie lecture(Long id) {
        return categorieRepository.findById(id).get();
    }

    public void suppression(Long id) {
        categorieRepository.deleteById(id);
    }

    public List<Categorie> liste() {
        return categorieRepository.findAll(Sort.by("nom").ascending());
    }
    public Page<Categorie> liste(int p) {
        return categorieRepository.findAll(PageRequest.of(p, Constante.NBRE_PAR_PAGE, Sort.by("nom").ascending()));
    }
}
