package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Personne;
import ml.pic.tech.app.alimentation.repository.PersonneRepository;
import ml.pic.tech.app.alimentation.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonneService {
    @Autowired
    private PersonneRepository personneRepository;

    public void ajout(Personne personne) {
        personneRepository.save(personne);
    }

    public Personne lecture(Long id) {
        return personneRepository.findById(id).get();
    }

    public void suppression(Long id) {
        personneRepository.deleteById(id);
    }

    public List<Personne> liste() {
        return personneRepository.findAll(Sort.by("prenom").ascending().
                and(Sort.by("nom").ascending()));
    }

    public Page<Personne> liste(int page) {
        return personneRepository.findAll(PageRequest.of(page, Constante.NBRE_PAR_PAGE,
                Sort.by("prenom").ascending().and(Sort.by("nom").ascending())));
    }
}
