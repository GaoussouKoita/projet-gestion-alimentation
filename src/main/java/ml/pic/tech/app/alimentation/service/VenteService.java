package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Vente;
import ml.pic.tech.app.alimentation.repository.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenteService {
    @Autowired
    private VenteRepository VenteRepository;

    public void ajout(Vente vente) {
        VenteRepository.save(vente);
    }

    public Vente lecture(Long id) {
        return VenteRepository.findById(id).get();
    }

    public void suppression(Long id) {
        VenteRepository.deleteById(id);
    }

    public List<Vente> liste() {
        return VenteRepository.findAll();
    }
    public Page<Vente> liste(int page) {
        return VenteRepository.findAll(PageRequest.of(page, 9));
    }
}
