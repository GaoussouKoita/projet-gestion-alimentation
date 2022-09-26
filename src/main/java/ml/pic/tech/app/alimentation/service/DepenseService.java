package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Depense;
import ml.pic.tech.app.alimentation.repository.DepenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepenseService {
    @Autowired
    private DepenseRepository depenseRepository;

    public void ajout(Depense depense) {
        depenseRepository.save(depense);
    }

    public Depense lecture(Long id) {
        return depenseRepository.findById(id).get();
    }

    public void suppression(Long id) {
        depenseRepository.deleteById(id);
    }

    public List<Depense> liste() {
        return depenseRepository.findAll();
    }
}
