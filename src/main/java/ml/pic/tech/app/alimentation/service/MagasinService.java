package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Magasin;
import ml.pic.tech.app.alimentation.repository.MagasinRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return magasinRepository.findAll();
    }
}
