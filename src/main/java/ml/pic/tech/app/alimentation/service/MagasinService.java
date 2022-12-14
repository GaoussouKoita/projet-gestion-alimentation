package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Magasin;
import ml.pic.tech.app.alimentation.repository.MagasinRepository;
import ml.pic.tech.app.alimentation.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagasinService {
    @Autowired
    private MagasinRepository magasinRepository;

    public void ajout(Magasin magasin) {

        magasinRepository.save(magasin);
    }

    public Magasin lecture(Long id) {
        return magasinRepository.findById(id).get();
    }

    public void suppression(Long id) {
        magasinRepository.deleteById(id);
    }

    public List<Magasin> liste() {
        return magasinRepository.findAll(Sort.by("nom").ascending());
    }
    public Page<Magasin> liste(int p) {
        return magasinRepository.findAll(PageRequest.of(p, Constante.NBRE_PAR_PAGE, Sort.by("nom").ascending()));
    }
}
